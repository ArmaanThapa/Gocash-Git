package com.gocash.api.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gocash.api.IUserApi;
import com.gocash.entity.ForgotPasswordDTO;
import com.gocash.entity.LoginLog;
import com.gocash.entity.User;
import com.gocash.entity.UserDetail;
import com.gocash.model.Status;
import com.gocash.model.UserDTO;
import com.gocash.model.UserType;
import com.gocash.model.request.RegisterDTO;
import com.gocash.repositories.LoginLogRepository;
import com.gocash.repositories.UserDetailRepository;
import com.gocash.repositories.UserRepository;
import com.gocash.util.Authorities;
import com.gocash.util.ClientException;
import com.gocash.util.CommonUtil;
import com.gocash.util.ConvertUtil;



public class UserApi  implements IUserApi{
	
	protected final Logger logger=LoggerFactory.getLogger(this.getClass());
	
	
	
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final UserDetailRepository userDetailRepository;
	private final LoginLogRepository loginLogRepository;
	
	public UserApi(PasswordEncoder passwordEncoder,UserRepository userRepository,UserDetailRepository userDetailRepository,
			LoginLogRepository loginLogRepository) {
		this.passwordEncoder=passwordEncoder;
		this.userRepository=userRepository;
		this.userDetailRepository=userDetailRepository;
		this.loginLogRepository=loginLogRepository;
	}

	@Override
	public void saveUser(RegisterDTO dto) throws ClientException
	{
		User user=new User();
		UserDetail detail=new UserDetail();
		detail.setEmail(dto.getEmail());
		detail.setContactNo(dto.getContactNo());
		detail.setFirstName(dto.getFirstName());
		detail.setGender(dto.getGender());
		detail.setLastName("lastname");
		
		User usertemper=userRepository.findByUsernameAndStatus(dto.getUsername(), Status.Inactive);
		{
			if(user==null)
			{
				userDetailRepository.save(detail);
				
			}
		}
		if(dto.getUserType().equals(UserType.User))
		{
			System.err.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
			System.err.println("in the implementiopn   "+dto.getContactNo());
			user.setUsername(dto.getContactNo().toLowerCase());
			user.setEmail(dto.getEmail());
			user.setFirstname(dto.getFirstName());
			user.setAuthority(Authorities.USER+","+Authorities.AUTHENTICATED);
			user.setPassword(passwordEncoder.encode(dto.getPassword()));
			user.setEmailStatus(Status.Active);
			user.setMobileStatus(Status.Active);
			user.setUserType(dto.getUserType());
			user.setUserDetail(detail);
			System.err.println("password    "+user.getPassword());
			String otp=CommonUtil.generateSixRandomNumericString();
			System.err.println("OTP  "+otp);
			user.setMobileToken(otp);
			userRepository.save(user);
			
		}	
		
	}

	@Override
	public User findByUserName(String username)
	{
		System.err.println(username);
		User user=userRepository.findByUsername(username);
		
		return user;
	}

	@Override
	public String handleLoginFailure(HttpServletRequest request, Object object, Authentication authentication,
			String username, String ipAddress) {
		User user=userRepository.findByUsername(username);
		if(user!=null)
		{
			System.err.println("user  "+user.getUsername());
			LoginLog log=new LoginLog();
			log.setUser(user);
			log.setRemoteAddress(ipAddress);
			log.setServerIp(request.getRemoteAddr());
			log.setStatus(Status.Failed);
			System.err.println(log.getUser());
			System.err.println(log.getServerIp()+"   ipserver");
			System.err.println(log.getRemoteAddress()+ "   remoteaddress");
			loginLogRepository.save(log);
			 
			List<LoginLog> llsFailed=loginLogRepository.findTodayEntryForUserWithStatus(user, Status.Failed);
			int failed=llsFailed.size();
			int remainingAttempts=getLoginAttempts()-failed;
			if(failed==remainingAttempts)
			{
				if(user.getUserType()==UserType.User)
				{
					
				}
				
				logger.info("Account blocked for user {}", user.getUsername());
				return "Your account is blocked! Please try after 24 hrs.";
			}
			
			logger.info("incorrect password for user {} attempts remaining {}", user.getUsername(),
					remainingAttempts);
			return "Incorrect password. Remaining attempts " + remainingAttempts;
		
			
		}
		
		return "Authentication Failed. Please try again";
	}

	
	public int getLoginAttempts() {
		return 5;
	}

	@Override
	public void handleLoginSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication, String loginUsername,String ipAddress)
	{
		if(loginUsername!=null)
		{
			User user=userRepository.findByUsername(loginUsername);
			if(user!=null)
			{
				LoginLog loginLog=new LoginLog();
				loginLog.setUser(user);
				loginLog.setRemoteAddress(ipAddress);
				loginLog.setServerIp(request.getRemoteAddr());
				loginLog.setStatus(Status.Success);
				loginLogRepository.save(loginLog);
				List<LoginLog> lls=loginLogRepository.findTodayEntryForUserWithStatus(user, Status.Failed);
				for (LoginLog ll : lls) {
					loginLogRepository.deleteLoginLogForId(Status.Deleted, ll.getId());
				}
			}
		}
		
		
	}

	@Override
	public UserDTO getUserById(Long id) {
		User u = userRepository.findOne(id);
		System.err.println("User mm   "+u);
		if (u != null) {
			return ConvertUtil.convertUser(u);
		}
		return null;
	}

	@Override
	public void changePasswordRequest(User user) {
		user.setMobileToken(CommonUtil.generateSixRandomNumericString());
		System.err.println("saving");
		userRepository.save(user);
		
	}

	@Override
	public void renewPassword(ForgotPasswordDTO dto) 
	{
		System.err.println(dto.getPassword());
		System.err.println(dto.getUsername());
		User user=userRepository.findByUsername(dto.getUsername());
		if(user!=null)
		{
			System.err.println("username "+user.getUsername());
			
			user.setPassword(passwordEncoder.encode(dto.getPassword()));
			user.setMobileToken(null);
			userRepository.save(user);

		}
		
	}

}
