package com.freetty.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.freetty.common.BeanUtil;
import com.freetty.dto.JsonDto;
import com.freetty.dto.PortfolioDto.DetailViewDto;
import com.freetty.dto.PortfolioDto.Portfolio2ServiceDto;
import com.freetty.dto.PortfolioDto.PortfolioManageDto;
import com.freetty.dto.PortfolioDto.RegistBookmark;
import com.freetty.dto.PortfolioDto.RegistWishlist;
import com.freetty.entity.ArtistKind;
import com.freetty.entity.ArtistPortfolio;
import com.freetty.entity.ArtistPortfolioService;
import com.freetty.entity.CustomerArtist;
import com.freetty.entity.CustomerArtistPK;
import com.freetty.entity.WishlistPortfolio;
import com.freetty.entity.WishlistPortfolioPK;
import com.freetty.exception.test.ErrorList;
import com.freetty.exception.test.FreettyLogicException;
import com.freetty.repository.ArtistKindRepo;
import com.freetty.repository.ArtistPortfolioRepo;
import com.freetty.repository.ArtistPortfolioServiceRepo;
import com.freetty.repository.ArtistRepo;
import com.freetty.repository.CustomerArtistRepo;
import com.freetty.repository.CustomerRepo;
import com.freetty.repository.WishlistPortfolioRepo;

/**
 * @Project : freetty-api-customer
 * @FileName : PortfolioServiceImpl.java
 * @Date : 2017. 3. 17.
 * @작성자 : 조성훈
 * @설명 :
 **/
@Service
@Transactional
public class PortfolioServiceImpl {

  @Autowired
  ArtistKindRepo artistKindRepo;
  @Autowired
  ArtistPortfolioRepo artistPortfolioRepo;
  @Autowired
  ArtistPortfolioServiceRepo artistPortfolioServiceRepo;
  @Autowired
  CustomerArtistRepo customerArtistRepo;
  @Autowired
  CustomerRepo customerRepo;
  @Autowired
  ArtistRepo artistRepo;
  @Autowired
  WishlistPortfolioRepo wishlistPortfolioRepo;

  public JsonDto<PortfolioManageDto> readByKindName(String kindName, Integer pageNum,
      Integer pageSize, Integer idfCustomer) {

    JsonDto<PortfolioManageDto> jDto = new JsonDto<PortfolioManageDto>();
    Pageable pageable = new PageRequest(pageNum, pageSize);
    Collection<Integer> idfLicenseKinds = null;
    switch (kindName) {
      case "hair":
        idfLicenseKinds = Arrays.asList(1, 5, 9, 12);
        break;
      case "lash":
        idfLicenseKinds = Arrays.asList(2, 6, 10, 13, 14, 16);
        break;
      case "makeup":
        idfLicenseKinds = Arrays.asList(3, 7, 11, 17);
        break;
      case "nail":
        idfLicenseKinds = Arrays.asList(4, 8, 15);
        break;
      case "barber":
        idfLicenseKinds = Arrays.asList(18);
        break;
    }

    jDto.setResultCode("S");
    jDto.setDataList(readPortfolioByIdfLicenseKind(idfLicenseKinds, pageable, idfCustomer));

    return jDto;
  }

  private List<PortfolioManageDto> readPortfolioByIdfLicenseKind(
      Collection<Integer> idfLicenseKinds, Pageable pageable, Integer idfCustomer) {

    List<ArtistKind> artistKindList =
        artistKindRepo.findByLicenseKindIdfLicenseKindIn(idfLicenseKinds);
    List<PortfolioManageDto> portfolioManageDtoList = new ArrayList<PortfolioManageDto>();

    Collection<Integer> idfArtistKinds = new ArrayList<Integer>();
    for (ArtistKind artistKind : artistKindList) {
      idfArtistKinds.add(artistKind.getIdfArtistKind());
    }

    for (ArtistPortfolio artistPortfolio : artistPortfolioRepo
        .findByArtistKindIdfArtistKindInOrderByIdfArtistPortfolioDesc(idfArtistKinds, pageable)) {

      PortfolioManageDto portfolioManageDto = new PortfolioManageDto();
      BeanUtil.copyProperties(artistPortfolio, portfolioManageDto);
      portfolioManageDto.setArtistName(artistPortfolio.getArtist().getName());
      portfolioManageDto.setIdfArtist(artistPortfolio.getArtist().getIdfArtist());

      // 해당 포트폴리오 위시리스트 좋아요 카운트
      Integer cntWishlist = wishlistPortfolioRepo
          .countByIdIdfArtistPortfolio(artistPortfolio.getIdfArtistPortfolio());
      portfolioManageDto.setCntWishlist(cntWishlist);

      // 해당 포트폴리오 위시리스트 좋아요 여부
      if (idfCustomer != 0 && customerRepo.exists(idfCustomer)) {
        WishlistPortfolioPK wpId = new WishlistPortfolioPK();
        wpId.setIdfArtistPortfolio(artistPortfolio.getIdfArtistPortfolio());
        wpId.setIdfCustomer(idfCustomer);
        if (wishlistPortfolioRepo.exists(wpId)) {
          portfolioManageDto.setIsWishlist("Y");
        } else {
          portfolioManageDto.setIsWishlist("N");
        }
      }

      if (artistPortfolio.getArtist().getArtistShop() != null)
        portfolioManageDto.setShopName(artistPortfolio.getArtist().getArtistShop().getName());

      if (artistPortfolioServiceRepo.countByArtistPortfolioIdfArtistPortfolio(
          portfolioManageDto.getIdfArtistPortfolio()) != 0) {
        ArtistPortfolioService artistPortfolioService =
            artistPortfolioServiceRepo.findByArtistPortfolioIdfArtistPortfolioAndType(
                portfolioManageDto.getIdfArtistPortfolio(), "M");
        portfolioManageDto.setLeadTime(artistPortfolioService.getLeadTime());
        portfolioManageDto.setPrice(artistPortfolioService.getPrice());
      }

      portfolioManageDtoList.add(portfolioManageDto);
    }
    return portfolioManageDtoList;

  }

  public JsonDto<?> readDetailByIdfArtistPortfolio(Integer idfArtistPortfolio,
      Integer idfCustomer) {
    JsonDto<DetailViewDto> jDto = new JsonDto<DetailViewDto>();
    DetailViewDto detailViewDto = new DetailViewDto();

    if (artistPortfolioRepo.exists(idfArtistPortfolio)) {
      ArtistPortfolio artistPortfolio = artistPortfolioRepo.findOne(idfArtistPortfolio);
      artistPortfolio.setCntView(artistPortfolio.getCntView() + 1);
      artistPortfolioRepo.save(artistPortfolio);

      BeanUtil.copyProperties(artistPortfolio, detailViewDto);
      detailViewDto.setArtistName(artistPortfolio.getArtist().getName());
      detailViewDto.setShopName(artistPortfolio.getArtist().getArtistShop().getName());
      detailViewDto.setIdfArtist(artistPortfolio.getArtist().getIdfArtist());


      // 해당 포트폴리오를 작성한 아티스트의 북마킹 여부
      CustomerArtistPK caId = new CustomerArtistPK();
      caId.setIdfArtist(artistPortfolio.getArtist().getIdfArtist());
      caId.setIdfCustomer(idfCustomer);
      if (customerArtistRepo.exists(caId)) {
        detailViewDto.setIsBookmark("Y");
      } else {
        detailViewDto.setIsBookmark("N");
      }

      // 해당 포트폴리오 위시리스트 좋아요 여부
      WishlistPortfolioPK wpId = new WishlistPortfolioPK();
      wpId.setIdfArtistPortfolio(idfArtistPortfolio);
      wpId.setIdfCustomer(idfCustomer);
      if (wishlistPortfolioRepo.exists(wpId)) {
        detailViewDto.setIsWishlist("Y");
      } else {
        detailViewDto.setIsWishlist("N");
      }

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

  public JsonDto<?> registBookmark(RegistBookmark registBookmark) {
    JsonDto<?> jDto = new JsonDto<>();

    CustomerArtistPK id = new CustomerArtistPK();
    id.setIdfArtist(registBookmark.getIdfArtist());
    id.setIdfCustomer(registBookmark.getIdfCustomer());
    CustomerArtist customerArtist = new CustomerArtist();
    customerArtist.setId(id);

    if (!customerArtistRepo.exists(id)) {
      if (artistRepo.exists(registBookmark.getIdfArtist())
          && customerRepo.exists(registBookmark.getIdfCustomer())) {
        customerArtistRepo.save(customerArtist);
        jDto.setResultCode("S");
        jDto.setResultMessage("regist bookmark");
      } else {
        jDto.setResultCode("F");
        jDto.setResultMessage("does not exist artist or customer");
      }
    } else {
      customerArtistRepo.delete(customerArtist);
      jDto.setResultCode("S");
      jDto.setResultMessage("remove bookmark");
    }

    return jDto;
  }

  public JsonDto<?> registWishlist(RegistWishlist registWishlist) {
    JsonDto<?> jDto = new JsonDto<>();

    WishlistPortfolioPK id = new WishlistPortfolioPK();
    id.setIdfArtistPortfolio(registWishlist.getIdfArtistPortfolio());
    id.setIdfCustomer(registWishlist.getIdfCustomer());
    WishlistPortfolio wishlistPortfolio = new WishlistPortfolio();
    wishlistPortfolio.setId(id);

    if (!wishlistPortfolioRepo.exists(id)) {
      if (artistPortfolioRepo.exists(registWishlist.getIdfArtistPortfolio())
          && customerRepo.exists(registWishlist.getIdfCustomer())) {
        wishlistPortfolioRepo.save(wishlistPortfolio);
        jDto.setResultCode("S");
        jDto.setResultMessage("regist wishlist portfolio");
      } else {
        jDto.setResultCode("F");
        jDto.setResultMessage("does not exist wishlist or customer");
      }
    } else {
      wishlistPortfolioRepo.delete(wishlistPortfolio);
      jDto.setResultCode("S");
      jDto.setResultMessage("remove wishlist portfolio");
    }

    return jDto;
  }

}
