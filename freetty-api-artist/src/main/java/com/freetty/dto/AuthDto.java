package com.freetty.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class AuthDto {
	
	private Integer idfArtist;
	@Email
	@NotBlank
	private String email;
	@NotBlank
	private String password;

	@Data
	public static class ValidateSMSCodeDto {
		private String mobile;
		private String smsCode;
		private String messageId;
	}
	
	@Data
	public static class ModifyPasswordDto {
		@NotNull		
		private Integer idfArtist;
		@NotBlank
		private String oldPassword;
		@NotBlank
		private String newPassword;
	}

	
}
