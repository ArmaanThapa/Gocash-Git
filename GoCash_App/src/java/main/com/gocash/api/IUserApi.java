package com.gocash.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

import com.gocash.entity.ForgotPasswordDTO;
import com.gocash.entity.User;
import com.gocash.model.UserDTO;
import com.gocash.model.request.RegisterDTO;
import com.gocash.util.ClientException;

public interface IUserApi {

	void saveUser(RegisterDTO user) throws ClientException;

	User findByUserName(String username);

	String handleLoginFailure(HttpServletRequest request, Object object, Authentication authentication, String valueOf,
			String ipAddress);
	
	
	void handleLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication, String loginUsername,String ipAddress);

	UserDTO getUserById(Long id);

	void changePasswordRequest(User user);

	void renewPassword(ForgotPasswordDTO dto);

}
