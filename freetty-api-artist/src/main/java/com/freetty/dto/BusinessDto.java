package com.freetty.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BusinessDto {

	@Data
	public static class BusinessListDto {
		private Integer idfArtist;		
		private String kindName;
		private String majorServiceDescription;
		private byte serviceDistance;
		private String shopName;
		private ArtistScheduleDto artistScheduleDto;
		private List<ArtistLicenseDto> artistLicenseDtoList;		
	}
	
	
	@Data
	public static class SaveLicenseImageDto {
		private Integer idfArtist;
		private String name;
		private MultipartFile licenseImage;
	}
	@Data
	public static class ConfirmLicenseDto {
		private Integer idfArtistLicense;		
		private String confirm;
	}
	

	@Data
	public static class SelectArtistKindDto {
		private Integer idfArtist;
		private String kindName;		
	}
	
	@Data
	public static class RegistMajorServiceDto {
		private Integer idfArtist;
		private String majorServiceDescription;
	}

	@Data
	public static class RegistServiceDistanceDto {
		private Integer idfArtist;
		private byte serviceDistance;
	}

	@Data
	public static class ArtistKindDto {
		private Integer idfArtistKind;
		private Integer idfArtist;
		private String licenseName;
		private String kindName;
		private String selectedKindName;
	}
	
}
