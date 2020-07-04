package com.freetty.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class CustomerDto {

	private int idfCustomer;
	private String platform;		
	private String email;		
	private String name;	
	private String nickname;	
	private String mobile;	
	private String birthday;
	private String gender;
	private String addr1;	
	private String addr2;
	private String specialDescription;	
	
	@Data
	public static class SaveProfileImageDto {
		private Integer idfCustomer;
		private MultipartFile profileImage;
	}
	
}
