package com.freetty.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class PortfolioDto {

	@Data
	public static class PortfolioImageDto {
		private Integer idfArtistPortfolio;
		private String type;
		private MultipartFile portfolioImage;
	}

	@Data
	public static class RegistPortfolioDto {
		private Integer idfArtistPortfolio;
		private Integer idfArtist;
		private Integer idfArtistKind;
		private String subject;
		private String description;
		private String tag;
		private String gender;
		private String kindName;
		private Integer readyTime;
		private String isBusinessTrip;
		private String isDeploy;
		private List<Portfolio2ServiceDto> serviceList;
	}

	@Data
	public static class Portfolio1Dto {
		private Integer idfArtistPortfolio;
		private Integer idfArtist;
		private Integer idfArtistKind;
		private String subject;
		private String description;
		private String tag;
		private String gender;
		private String kindName;
		private Integer readyTime;
		private String isBusinessTrip;
		private String isDeploy;
	}

	@Data
	public static class Portfolio2Dto {
		private Integer idfArtistPortfolio;
		private Integer readyTime;
		private String isBusinessTrip;
		private String isDeploy;
		private List<Portfolio2ServiceDto> serviceList;
	}

	@Data
	public static class Portfolio2ServiceDto {
		private Integer idfArtistPortfolioService;
		private String name;
		private Integer price;
		private Integer leadTime;
		private String type;
	}

	@Data
	public static class PortfolioManageDto {
		private Integer idfArtistPortfolio;
		private String artistName;
		private String shopName;
		private Integer price;
		private Integer leadTime;
		private String subject;
		private String tag;
		private Integer cntWishlist;
		private Integer cntView;
		private String isDeploy;
	}

	@Data
	public static class ModifyIsDeployDto {
		private Integer idfArtistPortfolio;
		private String isDeploy;
	}

	@Data
	public static class DetailViewDto {
		private Integer idfArtistPortfolio;
		private String subject;
		private String tag;
		private String description;
		private String artistName;
		private String shopName;
		private Integer cntWishlist;
		private Integer cntView;
		private List<Portfolio2ServiceDto> serviceList;
	}

}
