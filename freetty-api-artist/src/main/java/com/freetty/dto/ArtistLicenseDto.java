package com.freetty.dto;

import lombok.Data;

@Data
public class ArtistLicenseDto {
	private Integer idfArtist;
	private Integer idfArtistLicense;
	private String name;
	private String confirm;
	private String downloadUrl;
}
