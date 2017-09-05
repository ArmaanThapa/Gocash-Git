package com.gocash.util;

import com.gocash.entity.User;
import com.gocash.model.UserDTO;


public class ConvertUtil {

	public static UserDTO convertUser(User u) {
		
		

		UserDTO dto = new UserDTO();
		dto.setUserId(String.valueOf(u.getId()));
		dto.setAddress((u.getUserDetail().getAddress() == null) ? "" : u.getUserDetail().getAddress());
		dto.setContactNo(u.getUserDetail().getContactNo());
		dto.setFirstName(u.getUserDetail().getFirstName());
		dto.setMiddleName((u.getUserDetail().getMiddleName() == null) ? "" : u.getUserDetail().getMiddleName());
		dto.setLastName(u.getUserDetail().getLastName());
		dto.setEmail(u.getUserDetail().getEmail());
		dto.setMobileStatus(u.getMobileStatus());
		dto.setEmailStatus(u.getEmailStatus());
		dto.setUserType(u.getUserType());
		dto.setUsername(u.getUsername());
		dto.setAuthority(u.getAuthority());
		//dto.setDateOfBirth("" + u.getUserDetail().getDateOfBirth());
		/*dto.setGender(u.getUserDetail().getGender());
		dto.setMpin((u.getUserDetail().getMpin() == null) ? "" : u.getUserDetail().getMpin());
		dto.setImage((u.getUserDetail().getImage() == null) ? "" : u.getUserDetail().getImage());
		dto.setMpinPresent((u.getUserDetail().getMpin() == null) ? false : true);
		dto.setGcmId((u.getGcmId() == null) ? "" : u.getGcmId());*/
				return dto;
	
		
		
		
		
	}

}
