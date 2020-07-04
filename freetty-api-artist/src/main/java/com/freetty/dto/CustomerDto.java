package com.freetty.dto;

import lombok.Data;

@Data
public class CustomerDto {

	private String idfCustomer;
	private String platform;
	private String email;
	private String password;
	private String name;
	private String nickname;
	private String mobile;
	private String birthday;
	private String gender;
	private String addr1;
	private String addr2;
	private String special_description;
	
	@Data
	public static class sendInviteSMS {
		private Integer idfArtist;
		private String toName;
		private String mobile;
	}
	
	@Data
	public static class SearchCustomerDto {
		private Integer idfCustomer;
		private String name;
		private String addr1;
		private String birthday;
		private String isAdded;		
	}	
	


}
