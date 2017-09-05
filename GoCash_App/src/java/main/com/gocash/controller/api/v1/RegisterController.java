package com.gocash.controller.api.v1;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jettison.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gocash.api.IUserApi;
import com.gocash.entity.ForgotPasswordDTO;
import com.gocash.entity.User;
import com.gocash.model.Status;
import com.gocash.model.UserType;
import com.gocash.model.error.ChangePasswordError;
import com.gocash.model.error.ForgotPasswordError;
import com.gocash.model.error.RegisterError;
import com.gocash.model.request.RegisterDTO;
import com.gocash.model.response.ResponseDTO;
import com.gocash.model.response.ResponseStatus;
import com.gocash.validation.RegisterValidation;
import com.sun.jersey.core.header.MediaTypes;





@Controller
@RequestMapping("/Api/v1/{role}/{device}/{language}")
public class RegisterController {
	
	private final RegisterValidation registerValidation;
	private final IUserApi userApi;
	
	
	public RegisterController( RegisterValidation registerValidation,IUserApi userApi)
	{
		this.registerValidation=registerValidation;
		this.userApi=userApi;
		
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/Register",
			produces={ MediaType.APPLICATION_JSON_VALUE} ,consumes={MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<ResponseDTO> registerUser(@PathVariable(value = "role") String role,
			@PathVariable(value = "device") String device, @PathVariable(value = "language") String language,
			@RequestBody RegisterDTO user, @RequestHeader(value = "hash", required = true) String hash,
			HttpServletRequest request, HttpServletResponse response) throws JSONException, JsonGenerationException, JsonMappingException, IOException 
	{
		System.out.println("insider the register controller");
		System.err.println("contactno"+user.getContactNo());
		System.err.println("name"+user.getFirstName());
		System.err.println("email"+user.getEmail());
		ResponseDTO result = new ResponseDTO();
		
		
		
		if(role.equalsIgnoreCase("User"))
		{
	     RegisterError error=new RegisterError();
	     try {
	    	 user.setUsername(user.getContactNo());
	    	 error= registerValidation.validateNormalUser(user);
	    	 if(error.isValid())
	    	 {
	    		 System.err.println("after checking the validation ");
	    		 System.err.println("contact no   "+user.getContactNo());
	    		 
	    		 user.setUserType(UserType.User);
	    			userApi.saveUser(user);
	    			result.setStatus(ResponseStatus.SUCCESS);
	    			result.setMessage("You have successfully register.");
	    			result.setDetails("Otp has successfully send to "+user.getContactNo()+"  Please check your number");
	    			return new ResponseEntity<ResponseDTO>(result,HttpStatus.OK);
	    		 
	    	 }
	    	 
	    	 else
	    	 {
	    		 result.setStatus(ResponseStatus.FAILURE);
					result.setMessage(error.getMessage());
					result.setDetails(error);
					
	    	 }		
		} catch (Exception e) {
			result.setStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
			result.setMessage("Please try again later.");
			e.getMessage();
			return new ResponseEntity<ResponseDTO>(result, HttpStatus.OK);
		}
		}
		
		else{
			result.setStatus(ResponseStatus.UNAUTHORIZED_ROLE);
			result.setMessage("Failed, Unauthorized user.");
			result.setDetails("Failed, Unauthorized user.");
			return  new ResponseEntity<ResponseDTO>(result, HttpStatus.OK);
		}
		
		return  new ResponseEntity<ResponseDTO>(result, HttpStatus.OK);
		
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/ForgotPassword",
			produces={ MediaType.APPLICATION_JSON_VALUE} ,consumes={MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<ResponseDTO> forgotPassword(@PathVariable(value = "role") String role,
			@PathVariable(value = "device") String device, @PathVariable(value = "language") String language,
			@RequestBody ForgotPasswordDTO dto, @RequestHeader(value = "hash", required = true) String hash,
			HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException,Exception
	{
		ResponseDTO result=new ResponseDTO();
		
		//ChangePasswordError error=new ChangePasswordError();
		if(role.equalsIgnoreCase("User"))
		{
			ForgotPasswordError error= new ForgotPasswordError();
			String username=dto.getUsername();
			
			error=registerValidation.forgotPassword(username);
			if(error.isValid())
			{
				System.err.println("trueeeee");
				User user=userApi.findByUserName(username);
				if(user!=null)
				{
					if(user.getMobileStatus()==Status.Active)
					{
						try {
							userApi.changePasswordRequest(user);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						ForgotPasswordError passwordError =new ForgotPasswordError();
						passwordError=registerValidation.renewPasswordValidation(dto);
						if(passwordError.isValid())
						{
							userApi.renewPassword(dto);
							result.setStatus(ResponseStatus.SUCCESS);
							result.setMessage("Password Changed Successfully");
							result.setDetails("Password Changed Successfully");
							
							
						}
						else
						{
							

							result.setStatus(ResponseStatus.BAD_REQUEST);
							result.setMessage(passwordError.getMessage());
							result.setDetails(passwordError);
							
						
							
						}
						
						
						
						
						
						
					}
					else
					{

						result.setStatus(ResponseStatus.FAILURE);
						result.setMessage("User doesn't exist");
						result.setDetails("User doesn't exist");
						
					
					}
					
				}
				else
				{

					result.setStatus(ResponseStatus.FAILURE);
					result.setMessage("User doesn't exist");
					result.setDetails("User doesn't exist");
					
				}
				
			}
			else
			{
				result.setStatus(ResponseStatus.BAD_REQUEST);
				result.setMessage(error.getMessage());
				result.setDetails(error);
				
			}
				
		}
		else
		{
			result.setStatus(ResponseStatus.UNAUTHORIZED_ROLE);
			result.setMessage("Failed, Unauthorized user.");
			result.setDetails("Failed, Unauthorized user.");
			
		
		}
		return  new ResponseEntity<ResponseDTO>(result, HttpStatus.OK);
	}
}
	
