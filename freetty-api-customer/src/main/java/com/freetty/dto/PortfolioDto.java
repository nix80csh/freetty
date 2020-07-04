package com.freetty.dto;

import java.math.BigInteger;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class PortfolioDto {

  @Data
  public static class PortfolioManageDto {
    private Integer idfArtist;
    private Integer idfArtistPortfolio;
    private String artistName;
    private String shopName;
    private Integer price;
    private Integer leadTime;
    private String subject;
    private String tag;
    private String isDeploy;
    private String isWishlist;
    private Integer cntWishlist;
    private Integer cntView;
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
  public static class DetailViewDto {
    private Integer idfArtist;
    private Integer idfArtistPortfolio;
    private String subject;
    private String tag;
    private String description;
    private String artistName;
    private String shopName;
    private String isBookmark;
    private String isWishlist;
    private Integer cntWishlist;
    private Integer cntView;
    private String isBusinessTrip;
    private List<Portfolio2ServiceDto> serviceList;
  }

  @Data
  public static class RegistBookmark {
    private Integer idfArtist;
    private Integer idfCustomer;
  }

  @Data
  public static class RegistWishlist {
    private Integer idfArtistPortfolio;
    private Integer idfCustomer;
  }

}
