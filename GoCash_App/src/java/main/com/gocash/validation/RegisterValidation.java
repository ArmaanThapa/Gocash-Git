package com.gocash.validation;

import java.util.List;

import com.gocash.entity.ForgotPasswordDTO;
import com.gocash.entity.User;
import com.gocash.entity.UserDetail;
import com.gocash.model.Status;
import com.gocash.model.error.ChangePasswordError;
import com.gocash.model.error.ForgotPasswordError;
import com.gocash.model.error.RegisterError;
import com.gocash.model.request.RegisterDTO;
import com.gocash.repositories.UserDetailRepository;
import com.gocash.repositories.UserRepository;

public class RegisterValidation {
	
	
	private final UserRepository userRepository;
	private final UserDetailRepository userDetailRepository;

	
	public RegisterValidation(UserRepository userRepository,UserDetailRepository userDetailRepository) {
		this.userRepository=userRepository;
		this.userDetailRepository=userDetailRepository;
		
	}
	
	public RegisterError validateNormalUser(RegisterDTO dto)
	{
		System.err.println("inside the validation of user");
		
		RegisterError error=new RegisterError();
		boolean valid = true;
		
		
		if(dto.getUsername().isEmpty()||dto.getUsername()==null)
		{
			error.setMessage("Mobile Number is Compulsory.");
			valid=false;
			
		}
		else
		{
			User user=userRepository.findByUsernameAndStatus(dto.getUsername(), Status.Active);
			if(user!=null)
			{
				
				System.err.println(" contact number "+user.getUsername() +"and Stutus is "+  user.getMobileStatus());
				error.setMessage("You Have already register with this number");
				valid=false;
			}
			
			
		}
		
		
		
		if(dto.getContactNo().isEmpty()|| dto.getContactNo()==null)
		{
			error.setMessage("Mobile Number is Compulsory.");
			valid=false;
			
		}
		
		else if(!(CommonValidation.checkLength10(dto.getContactNo())))
		{
			
			error.setMessage("Mobile Number must be valid.");
			valid=false;
			
		}
		else if(!(CommonValidation.isNumeric(dto.getContactNo())))
		{
		
			error.setMessage("Mobile Number must be valid.");
			valid=false;
		}
		
		else if(!(CommonValidation.isValidMobileNumber(dto.getContactNo())))
		{
			error.setMessage("Mobile Number must be valid.");
			valid=false;	
		}
		
		
		
		if(dto.getEmail().isEmpty()|| dto.getEmail()==null)
		{
			error.setMessage("Email ID can not be left blank.");
			valid=false;
		}
		else if(!(CommonValidation.isValidMail(dto.getEmail())))
		{
			error.setMessage("Invalid email address.");
			valid=false;
			
		}
		
		
		if(dto.getPassword().isEmpty()|| dto.getPassword()==null)
		{
			error.setMessage("Password is Compulsory");
			valid=false;
			
		}
		else if(!(CommonValidation.emailValid(dto.getPassword())))
		{
			System.err.println("password  "  +dto.getPassword());
			error.setMessage(" Password must occur at least once  a digit , lower & Upper case letter  and at least 8 characters,no whitespace and special character,");
			valid=false;
		}
		
		if(dto.getFirstName().isEmpty()|| dto.getFirstName()==null)
		{
			error.setMessage("Please enter your name.");
			valid=false;
		}
		
		if (!CommonValidation.isNull(dto.getEmail())) {
			List<UserDetail> userDetail = userDetailRepository.checkMail(dto.getEmail());
			if (userDetail != null) {
				for (UserDetail ud : userDetail) {
					User user = userRepository.findByUsernameAndMobileStatusAndEmailStatus(ud.getContactNo(),
							Status.Active, Status.Active);
					if (user != null) {
						
						error.setMessage("Email already exists");
						valid = false;
					}
				}
				
			}
		}
		
		error.setValid(valid);
		return error;
	}

	public ForgotPasswordError renewPasswordValidation(ForgotPasswordDTO dto) {
		ForgotPasswordError error=new ForgotPasswordError();
		error.setValid(true);
		System.err.println("validationnnn");
		
		if(dto.getPassword().isEmpty()||dto.getPassword()==null)
		{
			error.setMessage("Password is Compulsory.");
			error.setValid(false);
			
		}
		else if(!(CommonValidation.emailValid(dto.getPassword())))		
		{
			error.setMessage(" Password must occur at least once  a digit , lower & Upper case letter  and at least 8 characters,no whitespace and special character,");
			error.setValid(false);
			
		}
		if(dto.getConfirmPassword().isEmpty()|| dto.getConfirmPassword()==null)
		{
			error.setMessage("ConfirmPassword is Compulsory.");
			error.setValid(false);
			
		}
		
		if(!(dto.getPassword().equals(dto.getConfirmPassword())))
		{
			error.setMessage("Password Mis-Match.");
			error.setValid(false);
			
		}
		
		
		
		return  error;
	}

	public ForgotPasswordError forgotPassword(String username) {
		
		System.err.println(username+"  userName");
		
		ForgotPasswordError error=new ForgotPasswordError();
		error.setValid(true);
		
		if(username.isEmpty()|| username==null)
		{
			error.setMessage("Mobile Number is Compulsory");
			error.setValid(false);
			
		}
		else if(!(CommonValidation.checkLength10(username)))
		{
			
			error.setMessage("Mobile Number must be valid.");
			error.setValid(false);
			
			
		}
		else if(!(CommonValidation.isNumeric(username)))
		{
		
			error.setMessage("Mobile Number must be valid.");
			error.setValid(false);
			
		}
		
		else if(!(CommonValidation.isValidMobileNumber(username)))
		{
			error.setMessage("Mobile Number must be valid.");
			error.setValid(false);
				
		}
		
		
		return error;
	}

}
