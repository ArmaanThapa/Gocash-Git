package com.gocash.validation;

import com.gocash.model.error.LoginError;
import com.gocash.model.request.LoginDTO;

public class LoginValidation {

	public LoginError validateLogin(LoginDTO dto) {
		System.err.println("inside the login validater");
		System.err.println(dto.getIpAddress());
		System.err.println(dto.getPassword());
		System.err.println(dto.getUsername());
		
		LoginError error=new LoginError();
		error.setSuccess(true);
		
		if(CommonValidation.isNull(dto.getUsername()))
		{
			error.setMessage("mobile number required");
			error.setSuccess(false);
		}
		if(CommonValidation.isNull(dto.getPassword()))
		{
			error.setMessage("password required");
			error.setSuccess(false);
		}
		if(CommonValidation.isNull(dto.getIpAddress()))
		{
			error.setMessage("not a device");
			error.setSuccess(false);
			
		}
		
		return error;
		
	}

}
