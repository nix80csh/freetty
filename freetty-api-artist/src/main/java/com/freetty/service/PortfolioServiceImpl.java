package com.freetty.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freetty.common.BeanUtil;
import com.freetty.common.CloudinaryUtil;
import com.freetty.common.ImageUtil;
import com.freetty.dto.JsonDto;
import com.freetty.dto.PortfolioDto.DetailViewDto;
import com.freetty.dto.PortfolioDto.ModifyIsDeployDto;
import com.freetty.dto.PortfolioDto.Portfolio1Dto;
import com.freetty.dto.PortfolioDto.Portfolio2Dto;
import com.freetty.dto.PortfolioDto.Portfolio2ServiceDto;
import com.freetty.dto.PortfolioDto.PortfolioImageDto;
import com.freetty.dto.PortfolioDto.PortfolioManageDto;
import com.freetty.dto.PortfolioDto.RegistPortfolioDto;
import com.freetty.entity.Artist;
import com.freetty.entity.ArtistKind;
import com.freetty.entity.ArtistPortfolio;
import com.freetty.entity.ArtistPortfolioService;
import com.freetty.repository.ArtistLicenseRepo;
import com.freetty.repository.ArtistPortfolioRepo;
import com.freetty.repository.ArtistPortfolioServiceRepo;
import com.freetty.repository.ArtistRepo;
import com.freetty.repository.WishlistPortfolioRepo;

@Service
@Transactional
public class PortfolioServiceImpl {

  @Autowired ArtistRepo artistRepo;
  @Autowired ArtistPortfolioRepo artistPortfolioRepo;
  @Autowired ArtistPortfolioServiceRepo artistPortfolioServiceRepo;
  @Autowired ArtistLicenseRepo artistLicenseRepo;
  @Autowired WishlistPortfolioRepo wishlistPortfolioRepo;

  @Autowired ImageUtil imageUtil;
  @Value("${cloud.aws.s3.bucket}") private String bucketName;

  @Autowired CloudinaryUtil cloudinaryUtil;
  @Value("${cloudinary.dirName}") private String dirName;

  public JsonDto<?> registPortfolioImage(PortfolioImageDto portfolioImageDto) {
    JsonDto<HashMap<String, String>> jDto = new JsonDto<HashMap<String, String>>();

    if (artistPortfolioRepo.exists(portfolioImageDto.getIdfArtistPortfolio())) {
      if (portfolioImageDto.getPortfolioImage() != null) {

        String fileName = portfolioImageDto.getPortfolioImage().getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        String extension = fileName.substring(index + 1);
        if (extension.equals("jpg")) {

          String uploadFileName = generateUploadFileName(portfolioImageDto.getType(),
              portfolioImageDto.getIdfArtistPortfolio());

          if (uploadFileName != "") {
            String url = cloudinaryUtil.uploadImage(dirName + "/portfolio", uploadFileName,
                portfolioImageDto.getPortfolioImage());
            // imageUtil.uploadImage(bucketName, "portfolio",
            // uploadFileName,
            // portfolioImageDto.getPortfolioImage());
            HashMap<String, String> set = new HashMap<String, String>();
            set.put("downloadUrl", url);
            jDto.setResultCode("S");
            jDto.setDataObject(set);
          } else {
            jDto.setResultCode("F");
            jDto.setResultMessage("wrong type value");
          }
        } else {
          jDto.setResultCode("F");
          jDto.setResultMessage("does not support extension");
        }
      } else {
        jDto.setResultCode("F");
        jDto.setResultMessage("does not exist multipart file");
      }
    } else {
      jDto.setResultCode("F");
      jDto.setResultMessage("does not exist portfolio");
    }
    return jDto;
  }

  public JsonDto<?> registPortfolio(RegistPortfolioDto registPortfolioDto) {
    JsonDto<RegistPortfolioDto> jDto = new JsonDto<RegistPortfolioDto>();
    ArtistPortfolio artistPortfolio = new ArtistPortfolio();
    RegistPortfolioDto registPortfolioDtoResponse = new RegistPortfolioDto();
    Artist artist = new Artist();
    ArtistKind artistKind = new ArtistKind();
    // 아티스트가 있으면
    if (artistRepo.exists(registPortfolioDto.getIdfArtist())) {
      artist.setIdfArtist(registPortfolioDto.getIdfArtist());
      artistKind.setIdfArtistKind(registPortfolioDto.getIdfArtistKind());
      artistPortfolio.setArtist(artist);
      artistPortfolio.setArtistKind(artistKind);
      BeanUtil.copyProperties(registPortfolioDto, artistPortfolio);
      artistPortfolio.setIsDeploy("Y");
      ArtistPortfolio savedArtistPortfolio = artistPortfolioRepo.save(artistPortfolio);

      if (artistPortfolioRepo.exists(savedArtistPortfolio.getIdfArtistPortfolio())) {

        if (registPortfolioDto.getServiceList() != null) {
          artistPortfolioServiceRepo.deleteByArtistPortfolioIdfArtistPortfolio(
              savedArtistPortfolio.getIdfArtistPortfolio());
          for (Portfolio2ServiceDto dto : registPortfolioDto.getServiceList()) {
            ArtistPortfolioService artistPortfolioService = new ArtistPortfolioService();
            artistPortfolioService.setArtistPortfolio(savedArtistPortfolio);
            BeanUtil.copyProperties(dto, artistPortfolioService);

            artistPortfolioServiceRepo.save(artistPortfolioService);
          }
        }
        jDto.setResultCode("S");
      } else {
        jDto.setResultCode("F");
        jDto.setResultMessage("does not exist portfolio");
      }

      registPortfolioDtoResponse
          .setIdfArtistPortfolio(savedArtistPortfolio.getIdfArtistPortfolio());
      jDto.setResultCode("S");
      jDto.setDataObject(registPortfolioDtoResponse);
    } else {
      jDto.setResultCode("F");
      jDto.setResultMessage("does not exist artist");
    }

    return jDto;
  }

  public JsonDto<?> checkToConfirmedLicense(Integer idfArtist, String confirm) {
    JsonDto<String> jDto = new JsonDto<String>();

    if (artistLicenseRepo.countByArtistIdfArtistAndConfirm(idfArtist, confirm) != 0) {
      jDto.setResultCode("S");
    } else {
      jDto.setResultCode("F");
      jDto.setResultMessage("does not exist confirmed license");
    }

    return jDto;
  }

  private String generateUploadFileName(String imageType, int idfArtistPortfolio) {

    String uploadFileName = "";
    switch (imageType) {
      case "before":
        uploadFileName = idfArtistPortfolio + "_before";
        break;
      case "after":
        uploadFileName = idfArtistPortfolio + "_after";
        break;
      case "option1":
        uploadFileName = idfArtistPortfolio + "_option1";
        break;
      case "option2":
        uploadFileName = idfArtistPortfolio + "_option2";
        break;
      case "option3":
        uploadFileName = idfArtistPortfolio + "_option3";
        break;
    }
    return uploadFileName;
  }

  public JsonDto<?> readAllByIdfArtist(Integer idfArtist, Integer pageNum, Integer pageSize) {
    JsonDto<PortfolioManageDto> jDto = new JsonDto<PortfolioManageDto>();
    Pageable pageable = new PageRequest(pageNum, pageSize);
    Page<ArtistPortfolio> artistPortfolioList = artistPortfolioRepo
        .findByArtistIdfArtistAndIsDeployOrderByIdfArtistPortfolioDesc(idfArtist, "Y", pageable);
    List<PortfolioManageDto> portfolioManageDtoList = new ArrayList<PortfolioManageDto>();
    for (ArtistPortfolio artistPortfolio : artistPortfolioList) {
      PortfolioManageDto portfolioManageDto = new PortfolioManageDto();

      BeanUtil.copyProperties(artistPortfolio, portfolioManageDto);
      portfolioManageDto.setArtistName(artistPortfolio.getArtist().getName());
      portfolioManageDto.setCntView(artistPortfolio.getCntView());

      // 포트폴리오 위시리스트 카운트
      Integer cntWishlist = wishlistPortfolioRepo
          .countByIdIdfArtistPortfolio(artistPortfolio.getIdfArtistPortfolio());
      portfolioManageDto.setCntWishlist(cntWishlist);


      if (artistPortfolio.getArtist().getArtistShop() != null)
        portfolioManageDto.setShopName(artistPortfolio.getArtist().getArtistShop().getName());

      if (artistPortfolioServiceRepo.countByArtistPortfolioIdfArtistPortfolio(
          portfolioManageDto.getIdfArtistPortfolio()) != 0) {
        ArtistPortfolioService artistPortfolioService =
            artistPortfolioServiceRepo.findByArtistPortfolioIdfArtistPortfolioAndType(
                portfolioManageDto.getIdfArtistPortfolio(), "M");
        portfolioManageDto.setPrice(artistPortfolioService.getPrice());
        portfolioManageDto.setLeadTime(artistPortfolioService.getLeadTime());
      }
      portfolioManageDtoList.add(portfolioManageDto);
    }
    jDto.setResultCode("S");
    jDto.setDataList(portfolioManageDtoList);
    return jDto;
  }

  public JsonDto<?> readByIdfArtistPortfolio1(Integer idfArtistPortfolio) {
    Portfolio1Dto portfolio1Dto = new Portfolio1Dto();
    JsonDto<Portfolio1Dto> jDto = new JsonDto<Portfolio1Dto>();
    try {
      ArtistPortfolio artistPortfolio = artistPortfolioRepo.findOne(idfArtistPortfolio);
      BeanUtil.copyProperties(artistPortfolio, portfolio1Dto);
      portfolio1Dto.setIdfArtist(artistPortfolio.getArtist().getIdfArtist());
      portfolio1Dto.setIdfArtistKind(artistPortfolio.getArtistKind().getIdfArtistKind());
      jDto.setResultCode("S");
      jDto.setDataObject(portfolio1Dto);
    } catch (IllegalArgumentException e) {
      jDto.setResultCode("F");
      jDto.setResultMessage("does not exist portfolio");
    }
    return jDto;
  }

  public JsonDto<?> readByIdfArtistPortfolio2(Integer idfArtistPortfolio) {
    Portfolio2Dto portfolio2Dto = new Portfolio2Dto();
    JsonDto<Portfolio2Dto> jDto = new JsonDto<Portfolio2Dto>();
    try {
      ArtistPortfolio artistPortfolio = artistPortfolioRepo.findOne(idfArtistPortfolio);
      BeanUtil.copyProperties(artistPortfolio, portfolio2Dto);
      List<ArtistPortfolioService> artistPortfolioServiceList =
          artistPortfolioServiceRepo.findByArtistPortfolioIdfArtistPortfolio(idfArtistPortfolio);
      List<Portfolio2ServiceDto> Portfolio2ServiceDtoList = new ArrayList<Portfolio2ServiceDto>();
      for (ArtistPortfolioService artistPortfolioService : artistPortfolioServiceList) {
        Portfolio2ServiceDto portfolio2ServiceDto = new Portfolio2ServiceDto();
        BeanUtil.copyProperties(artistPortfolioService, portfolio2ServiceDto);
        Portfolio2ServiceDtoList.add(portfolio2ServiceDto);
      }
      portfolio2Dto.setServiceList(Portfolio2ServiceDtoList);
      jDto.setResultCode("S");
      jDto.setDataObject(portfolio2Dto);
    } catch (IllegalArgumentException e) {
      jDto.setResultCode("F");
      jDto.setResultMessage("does not exist portfolio");
    }

    return jDto;
  }

  public JsonDto<?> deleteByIdfArtistPortfolio(Integer idfArtistPortfolio) {
    JsonDto<String> jDto = new JsonDto<String>();
    if (artistPortfolioRepo.exists(idfArtistPortfolio)) {

      // 포트폴리오 모든 이미지 삭제
      ArrayList<String> fileNameList = new ArrayList<String>();
      fileNameList.add(idfArtistPortfolio + "_before");
      fileNameList.add(idfArtistPortfolio + "_after");
      fileNameList.add(idfArtistPortfolio + "_option1");
      fileNameList.add(idfArtistPortfolio + "_option2");

      cloudinaryUtil.deleteImage("freetty/artist/portfolio/", fileNameList);
      artistPortfolioRepo.delete(idfArtistPortfolio);
      jDto.setResultCode("S");
    } else {
      jDto.setResultCode("F");
      jDto.setResultMessage("does not exist portfolio");
    }
    return jDto;
  }

  public JsonDto<?> deleteImage(String imageName) {
    JsonDto<String> jDto = new JsonDto<String>();
    boolean isDelete =
        cloudinaryUtil.deleteImage("freetty/artist/portfolio/", Arrays.asList(imageName));
    if (isDelete) {
      jDto.setResultCode("S");
    } else {
      jDto.setResultCode("F");
    }
    return jDto;
  }

  public JsonDto<?> modifyIsDeploy(ModifyIsDeployDto modifyIsDeployDto) {
    JsonDto<ModifyIsDeployDto> jDto = new JsonDto<ModifyIsDeployDto>();
    if (artistPortfolioRepo.exists(modifyIsDeployDto.getIdfArtistPortfolio())) {
      ArtistPortfolio artistPortfolio =
          artistPortfolioRepo.findOne(modifyIsDeployDto.getIdfArtistPortfolio());
      artistPortfolio.setIsDeploy(modifyIsDeployDto.getIsDeploy());
      artistPortfolioRepo.save(artistPortfolio);
      jDto.setResultCode("S");
    } else {
      jDto.setResultCode("F");
      jDto.setResultMessage("does not exist portfolio");
    }
    return jDto;
  }

  public JsonDto<?> readDetailByIdfArtistPortfolio(Integer idfArtistPortfolio) {
    JsonDto<DetailViewDto> jDto = new JsonDto<DetailViewDto>();
    DetailViewDto detailViewDto = new DetailViewDto();
    if (artistPortfolioRepo.exists(idfArtistPortfolio)) {
      ArtistPortfolio artistPortfolio = artistPortfolioRepo.findOne(idfArtistPortfolio);
      BeanUtil.copyProperties(artistPortfolio, detailViewDto);
      detailViewDto.setArtistName(artistPortfolio.getArtist().getName());
      detailViewDto.setShopName(artistPortfolio.getArtist().getArtistShop().getName());
      detailViewDto.setCntView(artistPortfolio.getCntView());

      // 포트폴리오 위시리스트 카운트
      Integer cntWishlist = wishlistPortfolioRepo.countByIdIdfArtistPortfolio(idfArtistPortfolio);
      detailViewDto.setCntWishlist(cntWishlist);

      List<Portfolio2ServiceDto> portfolio2ServiceDtoList = new ArrayList<Portfolio2ServiceDto>();
      List<ArtistPortfolioService> artistPortfolioServiceList =
          artistPortfolioServiceRepo.findByArtistPortfolioIdfArtistPortfolio(idfArtistPortfolio);
      for (ArtistPortfolioService artistPortfolioService : artistPortfolioServiceList) {
        Portfolio2ServiceDto portfolio2ServiceDto = new Portfolio2ServiceDto();
        BeanUtil.copyProperties(artistPortfolioService, portfolio2ServiceDto);
        portfolio2ServiceDtoList.add(portfolio2ServiceDto);
      }
      detailViewDto.setServiceList(portfolio2ServiceDtoList);
      jDto.setResultCode("S");
      jDto.setDataObject(detailViewDto);
    } else {
      jDto.setResultCode("F");
      jDto.setResultMessage("does not exist portfolio");
    }
    return jDto;
  }

  public JsonDto<?> readServiceDistanceByIdfArtist(Integer idfArtist) {

    Map<String, Byte> map = new HashMap<String, Byte>();
    JsonDto<Map> jDto = new JsonDto<Map>();

    if (artistRepo.exists(idfArtist)) {
      Artist artist = artistRepo.findOne(idfArtist);
      map.put("serviceDistance", artist.getServiceDistance());
      jDto.setResultCode("S");
      jDto.setDataObject(map);
    } else {
      jDto.setResultCode("F");
      jDto.setResultMessage("does not exist artist");
    }

    return jDto;
  }

}
