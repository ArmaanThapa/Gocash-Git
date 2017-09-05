package com.gocash.controller.api.v1;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;

import com.gocash.api.ISessionApi;
import com.gocash.api.IUserApi;
import com.gocash.entity.User;
import com.gocash.entity.UserSession;
import com.gocash.model.Status;
import com.gocash.model.UserDTO;
import com.gocash.model.UserType;
import com.gocash.model.error.AuthenticationError;
import com.gocash.model.error.LoginError;
import com.gocash.model.request.LoginDTO;
import com.gocash.model.request.SessionDTO;
import com.gocash.model.response.ResponseDTO;
import com.gocash.model.response.ResponseStatus;
import com.gocash.repositories.UserSessionRepository;
import com.gocash.session.SessionLoggingStrategy;
import com.gocash.util.Authorities;
import com.gocash.util.CommonUtil;
import com.gocash.validation.LoginValidation;

@Controller
@RequestMapping("/Api/v1/{role}/{device}/{language}")

public class LoginController {
	
	protected final Logger logger=LoggerFactory.getLogger(this.getClass());
	
	private final LoginValidation loginValidation;
	private final IUserApi userApi;
	private final ISessionApi sessionApi;
	private final AuthenticationManager  authenticationManager;
	private final SessionLoggingStrategy sessionLoggingStrategy;
	private final UserSessionRepository userSessionRepository;
	public LoginController(LoginValidation loginValidation,IUserApi userApi,
			ISessionApi sessionApi, AuthenticationManager  authenticationManager,SessionLoggingStrategy sessionLoggingStrategy,UserSessionRepository userSessionRepository) {
		this.loginValidation=loginValidation;
		this.userApi=userApi;
		this.sessionApi=sessionApi;
		this.authenticationManager=authenticationManager;
		this.sessionLoggingStrategy=sessionLoggingStrategy;
		this.userSessionRepository=userSessionRepository;
		
	}
	
	@RequestMapping(value="/Login",  method=RequestMethod.POST, produces=(MediaType.APPLICATION_JSON_VALUE) ,consumes=(MediaType.APPLICATION_JSON_VALUE))
	ResponseEntity<ResponseDTO>  userLogin(@PathVariable (value="role") String role, @PathVariable (value="device") String device,
			@PathVariable(value="language") String language, @RequestBody LoginDTO dto,@RequestHeader(value = "hash", required = true) String hash,
			HttpServletRequest request, HttpServletResponse response ) throws ServletException,IOException,Exception
	{
		
		InetAddress addr = InetAddress.getLocalHost();
		String ipAddress=addr.getHostAddress();
	    System.out.println("Name of Ipaddress : " + ipAddress);  
	    System.err.println(dto.getUsername());
	    System.out.println(dto.getPassword());
	    dto.setIpAddress(ipAddress);
		LoginError error=new LoginError();
		ResponseDTO result=new ResponseDTO();
		error=loginValidation.validateLogin(dto);
		if(error.isSuccess())
		{
			try {
				
				User user=userApi.findByUserName(dto.getUsername());
				if(user!=null)
				{
					System.err.println("oooooooooooooooooooooooooo");
					if(role.equalsIgnoreCase("User"))
					{
						if(user.getAuthority().contains(Authorities.USER))
						{
						System.err.println("//////////");
						if(!dto.isValidate())
						{
							System.err.println("dddddddddddd");
							if(sessionApi.checkActiveSession(user))
								
							{
								AuthenticationError auth=authentication(request,dto);
								if(auth.isSuccess())
								{
									System.err.println("yahoooooo");
									user.setMobileToken(CommonUtil.generateSixRandomNumericString());
									Map <String,Object> details=new HashMap<String,Object>();
									Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
									sessionLoggingStrategy.onAuthentication(authentication, request, response);
									UserSession session=userSessionRepository.findByActiveSessionId(
											RequestContextHolder.currentRequestAttributes().getSessionId());
									System.err.println(session);
									logger.info("::" + RequestContextHolder.currentRequestAttributes());

									UserDTO activeUser=userApi.getUserById(session.getUser().getId());
									result.setStatus(ResponseStatus.SUCCESS);
									result.setMessage("Login successful.");
									details.put("sessionId", session.getSessionId());
									details.put("userDetail", activeUser);
									result.setDetails(details);
									return new ResponseEntity<ResponseDTO>(result, HttpStatus.OK);
								}
								else
								{

									result.setStatus(ResponseStatus.UNAUTHORIZED_USER);
									result.setMessage(auth.getMessage());
									result.setDetails(null);
									return new ResponseEntity<ResponseDTO>(result, HttpStatus.OK);
								
								}
							}
							else
							{

								result.setStatus(ResponseStatus.BAD_REQUEST);
								result.setMessage("Not a valid OTP");
							
							}
							
							
						}
						}
						else
						{

							result.setStatus(ResponseStatus.UNAUTHORIZED_USER);
							result.setMessage("Failed, Unauthorized user.");
							result.setDetails(null);
							return new ResponseEntity<ResponseDTO>(result, HttpStatus.OK);
						
						}
						
					}
					else
					{

						result.setStatus(ResponseStatus.UNAUTHORIZED_USER);
						//result.setMessage(auth.getMessage());
						result.setDetails(null);
						return new ResponseEntity<ResponseDTO>(result, HttpStatus.OK);
					}
					
				}
				else
				{
					System.err.println("no is not persent");

					result.setStatus(ResponseStatus.UNAUTHORIZED_USER);
					result.setMessage("Failed, Unauthorized user.");
					result.setDetails(null);
					return new ResponseEntity<ResponseDTO>(result, HttpStatus.OK);
				
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				result.setStatus(ResponseStatus.BAD_REQUEST);
				result.setMessage("Failed, invalid request.");
				result.setDetails("Failed, invalid request.");
				return new ResponseEntity<ResponseDTO>(result, HttpStatus.OK);
			}
			
		}
		else
		{
			System.err.println(error.getMessage());
			result.setStatus(ResponseStatus.BAD_REQUEST);
			result.setMessage(error.getMessage());
			result.setDetails(error);
			return new ResponseEntity<ResponseDTO>(result,HttpStatus.OK);
			
		}
		
		return new ResponseEntity<ResponseDTO>(result,HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value="/Logout",method=RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE},consumes={MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<ResponseDTO> logOutUserApi(@PathVariable(value="role") String role,@PathVariable(value="language") String language,
			@PathVariable(value="device") String device , @RequestBody SessionDTO sessionId, @RequestHeader(value = "hash", required = true) String hash,
			HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException,Exception
	{
		ResponseDTO result = new ResponseDTO();
		UserSession session=userSessionRepository.findBySessionId(sessionId.getSessionId());
		
		try {
			if(session!=null)
			{
				sessionApi.expireSession(session.getSessionId());
				result.setStatus(ResponseStatus.SUCCESS);
				result.setMessage("User logout successful");
				result.setDetails("Session Out");
				return new ResponseEntity<ResponseDTO>(result, HttpStatus.OK);
				
			}
			else
			{
				result.setStatus(ResponseStatus.INVALID_SESSION);
				result.setMessage("Please, login and try again.");
				result.setDetails("Please, login and try again.");
				return new ResponseEntity<ResponseDTO>(result, HttpStatus.OK);
			
				
			}
			
		} catch (Exception e) {
			result.setStatus(ResponseStatus.BAD_REQUEST);
			result.setMessage("Failed, invalid request.");
			result.setDetails("Failed, invalid request.");
			return new ResponseEntity<ResponseDTO>(result, HttpStatus.OK);
		}
		
		
		
		
	}



	private AuthenticationError authentication(HttpServletRequest request, LoginDTO dto) throws 
	ServletException,IOException,Exception
	{
		AuthenticationError error=new AuthenticationError();
		Authentication authentication=null;
		UsernamePasswordAuthenticationToken token=null;
		
		try
		{
			
			token=new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
			logger.info("TOKEN ::" + String.valueOf(token.getPrincipal()));
			System.err.println(token.isAuthenticated());
			System.err.println(token.toString());
			authentication = authenticationManager.authenticate(token);
			System.out.println(authentication+"  authentication");
			logger.info("AUTH NAME :: " + authentication.getName());
			logger.info("AUTH CREDENTIALS :: " + authentication.getCredentials());
			logger.info("AUTH AUTHORITY :: " + authentication.getAuthorities());
			SecurityContext securityContext = SecurityContextHolder.getContext();
			if(authentication.isAuthenticated())
			{
				System.err.println("armaan thapa");
				logger.info("AUTH :: Authenticated");
				securityContext.setAuthentication(authentication);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				HttpSession httpSession=request.getSession(true);
				httpSession.setAttribute("SPRING_SECURITY_CONTEXT",securityContext);
				error.setMessage("Login successful.");
				error.setSuccess(true);
				userApi.handleLoginSuccess(request, null, authentication, String.valueOf(token.getPrincipal()),dto.getIpAddress());

				return error;
				
			}
			
		}
		catch (Exception e) {
			SecurityContextHolder.getContext().setAuthentication(null);
			error.setSuccess(false);
			System.err.println("thapa");
			error.setMessage(userApi.handleLoginFailure(request, null, authentication, String.valueOf(token.getPrincipal()),dto.getIpAddress()));
			return error;
			
		}
		
		return null;
	}

}
