package com.freetty.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ArtistDto {

	private Integer idfArtist;
	private String email;
	private String name;
	private String nickname;
	private String birthday;
	private String gender;
	private String mobile;
	private String introduce;
	private String languageChi;
	private String languageEng;
	private String languageJap;
	private String languageSign;

	@Data
	public static class SaveProfileImageDto {
		private Integer idfArtist;
		private MultipartFile profileImage;
	}

	@Data
	public static class ArtistCustomerDto {
		private Integer idfCustomer;
		private Integer idfArtist;
		private byte discountRate;
		private String description;
	}
	
	@Data
	public static class AddArtistCustomerDto {		
		private Integer idfCustomer;		
		private Integer idfArtist;
	}
	
	@Data 
	public static class ModifyReservationPolicy {
		private Integer idfArtist;
		private String reservationPolicy;
	}
	
	
	

}
