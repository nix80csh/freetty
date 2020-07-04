package com.freetty.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.freetty.common.BeanUtil;
import com.freetty.common.CloudinaryUtil;
import com.freetty.common.ImageUtil;
import com.freetty.dto.ArtistLicenseDto;
import com.freetty.dto.ArtistScheduleDto;
import com.freetty.dto.ArtistShopDto;
import com.freetty.dto.BusinessDto.ArtistKindDto;
import com.freetty.dto.BusinessDto.BusinessListDto;
import com.freetty.dto.BusinessDto.ConfirmLicenseDto;
import com.freetty.dto.BusinessDto.RegistMajorServiceDto;
import com.freetty.dto.BusinessDto.RegistServiceDistanceDto;
import com.freetty.dto.BusinessDto.SaveLicenseImageDto;
import com.freetty.dto.BusinessDto.SelectArtistKindDto;
import com.freetty.dto.JsonDto;
import com.freetty.entity.Artist;
import com.freetty.entity.ArtistKind;
import com.freetty.entity.ArtistLicense;
import com.freetty.entity.ArtistPortfolio;
import com.freetty.entity.ArtistSchedule;
import com.freetty.entity.ArtistShop;
import com.freetty.entity.LicenseKind;
import com.freetty.entity.model.ArtistBusiness;
import com.freetty.repository.ArtistKindRepo;
import com.freetty.repository.ArtistLicenseRepo;
import com.freetty.repository.ArtistPortfolioRepo;
import com.freetty.repository.ArtistRepo;
import com.freetty.repository.ArtistScheduleRepo;
import com.freetty.repository.ArtistShopRepo;
import com.freetty.repository.LicenseKindRepo;

@Service
@Transactional
public class BusinessServiceImpl {

  @Autowired ArtistRepo artistRepo;
  @Autowired ArtistKindRepo artistKindRepo;
  @Autowired ArtistLicenseRepo artistLicenseRepo;
  @Autowired ArtistPortfolioRepo artistPortfolioRepo;

  @Autowired LicenseKindRepo licenseKindRepo;
  @Autowired ArtistScheduleRepo artistScheduleRepo;
  @Autowired ArtistShopRepo artistShopRepo;
  @Autowired ImageUtil imageUtil;

  @Autowired CloudinaryUtil cloudinaryUtil;
  @Value("${cloud.aws.s3.bucket}") private String bucketName;
  @Value("${cloudinary.dirName}") private String dirName;

  public JsonDto<?> readLicenseByIdfArtist(Integer idfArtist) {
    JsonDto<ArtistLicenseDto> jDto = new JsonDto<ArtistLicenseDto>();
    List<ArtistLicense> artistLicenseList = artistLicenseRepo.findByArtistIdfArtist(idfArtist);
    List<ArtistLicenseDto> artistLicenseDtoList = new ArrayList<ArtistLicenseDto>();

    for (ArtistLicense al : artistLicenseList) {
      ArtistLicenseDto dto = new ArtistLicenseDto();
      BeanUtil.copyProperties(al, dto);
      dto.setIdfArtist(al.getArtist().getIdfArtist());
      artistLicenseDtoList.add(dto);
    }

    jDto.setResultCode("S");
    jDto.setDataList(artistLicenseDtoList);
    return jDto;
  }

  public JsonDto<?> registLicenseImage(SaveLicenseImageDto saveLicenseImageDto) {
    JsonDto<ArtistLicenseDto> jDto = new JsonDto<ArtistLicenseDto>();
    if (artistRepo.exists(saveLicenseImageDto.getIdfArtist())) {
      String fileName = saveLicenseImageDto.getLicenseImage().getOriginalFilename();
      int index = fileName.lastIndexOf(".");
      String extension = fileName.substring(index + 1);

      if (extension.equals("jpg")) {

        String uploadFileName = "";
        int idfArtist = saveLicenseImageDto.getIdfArtist();

        switch (saveLicenseImageDto.getName()) {
          case "normal_1":
            uploadFileName = idfArtist + "_normal_1";
            break;
          case "normal_2":
            uploadFileName = idfArtist + "_normal_2";
            break;
          case "normal_3":
            uploadFileName = idfArtist + "_normal_3";
            break;
          case "normal_4":
            uploadFileName = idfArtist + "_normal_4";
            break;
          case "skin":
            uploadFileName = idfArtist + "_skin";
            break;
          case "nail":
            uploadFileName = idfArtist + "_nail";
            break;
          case "makeup":
            uploadFileName = idfArtist + "_makeup";
            break;
          case "barber":
            uploadFileName = idfArtist + "_barber";
            break;
        }

        if (uploadFileName != "") {
          ArtistLicense artistLicense = new ArtistLicense();
          Artist artist = new Artist();
          ArtistLicenseDto artistLicenseDto = new ArtistLicenseDto();
          artistLicenseRepo.deleteByArtistIdfArtistAndName(saveLicenseImageDto.getIdfArtist(),
              saveLicenseImageDto.getName());

          artist.setIdfArtist(saveLicenseImageDto.getIdfArtist());
          artistLicense.setArtist(artist);
          artistLicense.setName(saveLicenseImageDto.getName());
          artistLicense.setConfirm("R");
          artistLicenseRepo.save(artistLicense);
          BeanUtil.copyProperties(artistLicense, artistLicenseDto);
          artistLicenseDto.setIdfArtist(artistLicense.getArtist().getIdfArtist());
          String url = cloudinaryUtil.uploadImage(dirName + "/license", uploadFileName,
              saveLicenseImageDto.getLicenseImage());
          // imageUtil.uploadImage(bucketName, "license",
          // uploadFileName,
          // saveLicenseImageDto.getLicenseImage());
          artistLicenseDto.setDownloadUrl(url);
          jDto.setResultCode("S");
          jDto.setDataObject(artistLicenseDto);
        } else {
          jDto.setResultCode("F");
          jDto.setResultMessage("wrong type name");
        }

      } else {
        jDto.setResultCode("F");
        jDto.setResultMessage("does not support extension");
      }
    } else {
      jDto.setResultCode("F");
      jDto.setResultMessage("does not exist artist");
    }
    return jDto;
  }

  public JsonDto<?> selectArtistKind(SelectArtistKindDto selectArtistKindDto) {
    JsonDto<SelectArtistKindDto> jDto = new JsonDto<SelectArtistKindDto>();

    if (artistRepo.exists(selectArtistKindDto.getIdfArtist())) {
      Artist artist = artistRepo.findOne(selectArtistKindDto.getIdfArtist());
      artist.setKindName(selectArtistKindDto.getKindName());
      artistRepo.save(artist);
      jDto.setResultCode("S");
    } else {
      jDto.setResultCode("F");
      jDto.setResultMessage("does not exist artist");
    }

    return jDto;
  }

  public JsonDto<?> registMajorService(RegistMajorServiceDto registMajorServiceDto) {
    JsonDto<RegistMajorServiceDto> jDto = new JsonDto<RegistMajorServiceDto>();
    if (artistRepo.exists(registMajorServiceDto.getIdfArtist())) {
      Artist artist = artistRepo.findOne(registMajorServiceDto.getIdfArtist());
      BeanUtil.copyProperties(registMajorServiceDto, artist);
      artistRepo.save(artist);
      jDto.setResultCode("S");
      // jDto.setDataObject(selectMajorDto);
    } else {
      jDto.setResultCode("F");
      jDto.setResultMessage("does not exist artist");
    }
    return jDto;
  }

  /**
   * @작성일 : 2017. 3. 24.
   * @설명 :
   * 
   *     <pre>
   *     아티스트가 비지니스 설정에서 출장여부를 설정한다 
   *     출장여부를 출장거리가 0보다 크면 출장이 가능한 상태로 보고 값을 사용하고 
   *     출장이 가능한 상태에서(출장거리가  0보다 큰상태) 출장이 불가능한 상태(출장거리가 0인 상태)로 
   *     변경할 경우 공개여부를(IsDeploy) 모두 비공개로 바꾸어준다
   *     </pre>
   **/
  public JsonDto<?> registServiceDistance(RegistServiceDistanceDto registServiceDistanceDto) {
    JsonDto<RegistServiceDistanceDto> jDto = new JsonDto<RegistServiceDistanceDto>();
    if (artistRepo.exists(registServiceDistanceDto.getIdfArtist())) {
      Artist artist = artistRepo.findOne(registServiceDistanceDto.getIdfArtist());
      BeanUtil.copyProperties(registServiceDistanceDto, artist);
      artistRepo.save(artist);

      if (artist.getServiceDistance() == 0) {
        List<ArtistPortfolio> newArtistPortfolioList = new ArrayList<ArtistPortfolio>();
        Page<ArtistPortfolio> artistPortfolioList =
            artistPortfolioRepo.findByArtistIdfArtistAndIsDeployOrderByIdfArtistPortfolioDesc(
                artist.getIdfArtist(), "Y", null);

        for (ArtistPortfolio ap : artistPortfolioList) {
          ap.setIsDeploy("N");
          newArtistPortfolioList.add(ap);
        }
        artistPortfolioRepo.save(newArtistPortfolioList);
      }

      jDto.setResultCode("S");
      // jDto.setDataObject(selectMajorDto);
    } else {
      jDto.setResultCode("F");
      jDto.setResultMessage("does not exist artist");
    }
    return jDto;
  }

  public JsonDto<?> confirmLicense(ConfirmLicenseDto confirmLicenseDto) {
    JsonDto<ConfirmLicenseDto> jDto = new JsonDto<ConfirmLicenseDto>();
    ArtistLicense artistLicense =
        artistLicenseRepo.findOne(confirmLicenseDto.getIdfArtistLicense());
    BeanUtil.copyProperties(confirmLicenseDto, artistLicense);
    artistLicenseRepo.save(artistLicense);
    registArtistKind(artistLicense.getArtist().getIdfArtist(), artistLicense.getName());

    jDto.setResultCode("S");
    return jDto;
  }

  private void registArtistKind(int idfArtist, String licenseName) {
    artistKindRepo.deleteByArtistIdfArtistAndLicenseName(idfArtist, licenseName);
    Artist artist = new Artist();
    artist.setIdfArtist(idfArtist);
    List<LicenseKind> licenseKindList = licenseKindRepo.findByLicenseName(licenseName);
    for (LicenseKind licenseKind : licenseKindList) {
      ArtistKind artistKind = new ArtistKind();
      artistKind.setArtist(artist);
      artistKind.setLicenseName(licenseName);
      artistKind.setKindName(licenseKind.getKindName());
      artistKind.setLicenseKind(licenseKind);
      artistKindRepo.save(artistKind);
    }
  }

  public JsonDto<?> readKindByIdfArtist(Integer idfArtist) {
    JsonDto<ArtistKindDto> jDto = new JsonDto<ArtistKindDto>();
    List<ArtistKindDto> ArtistKindDtoList = new ArrayList<ArtistKindDto>();
    List<ArtistKind> artistKindList = artistKindRepo.findByArtistIdfArtist(idfArtist);
    String selectedKindName = artistRepo.findKindNameByIdfArtist(idfArtist);
    for (ArtistKind artistKind : artistKindList) {
      ArtistKindDto dto = new ArtistKindDto();
      BeanUtil.copyProperties(artistKind, dto);
      dto.setSelectedKindName(selectedKindName);
      ArtistKindDtoList.add(dto);
    }

    jDto.setResultCode("S");
    jDto.setDataList(ArtistKindDtoList);
    return jDto;
  }

  public JsonDto<?> readBusinessListByIdfArtist(Integer idfArtist) {
    JsonDto<BusinessListDto> jDto = new JsonDto<BusinessListDto>();
    BusinessListDto businessListDto = new BusinessListDto();
    if (artistRepo.exists(idfArtist)) {
      businessListDto.setIdfArtist(idfArtist);
      // 자격증 리스트 조회
      if (artistLicenseRepo.countByArtistIdfArtist(idfArtist) != 0) {
        List<ArtistLicense> artistLicenseList = artistLicenseRepo.findByArtistIdfArtist(idfArtist);
        List<ArtistLicenseDto> artistLicenseDtoList = new ArrayList<ArtistLicenseDto>();
        for (ArtistLicense artistLicense : artistLicenseList) {
          ArtistLicenseDto artistLicenseDto = new ArtistLicenseDto();
          BeanUtil.copyProperties(artistLicense, artistLicenseDto);
          artistLicenseDto.setIdfArtist(null);
          artistLicenseDtoList.add(artistLicenseDto);
        }
        businessListDto.setArtistLicenseDtoList(artistLicenseDtoList);
      }

      // 스케쥴 조회
      if (artistScheduleRepo.exists(businessListDto.getIdfArtist())) {
        ArtistSchedule artistSchedule = artistScheduleRepo.findOne(businessListDto.getIdfArtist());
        ArtistScheduleDto artistScheduleDto = new ArtistScheduleDto();
        BeanUtil.copyProperties(artistSchedule, artistScheduleDto);
        artistScheduleDto.setIdfArtist(null);
        businessListDto.setArtistScheduleDto(artistScheduleDto);
      }

      // 샵이름 조회
      if (artistShopRepo.exists(businessListDto.getIdfArtist())) {
        ArtistShop artistShop = artistShopRepo.findOne(businessListDto.getIdfArtist());
        businessListDto.setShopName(artistShop.getName());
      }

      // 아티스트 기본정보 조회
      ArtistBusiness artistBusiness =
          artistRepo.findArtistBusinessByIdfArtist(businessListDto.getIdfArtist());
      businessListDto.setKindName(artistBusiness.getKindName());
      businessListDto.setMajorServiceDescription(artistBusiness.getMajorServiceDescription());
      businessListDto.setServiceDistance(artistBusiness.getServiceDistance());

      jDto.setResultCode("S");
      jDto.setDataObject(businessListDto);
    } else {
      jDto.setResultCode("F");
      jDto.setResultMessage("does not exist artist");
    }

    return jDto;
  }

  public JsonDto<?> readArtistShopByIdfArtist(Integer idfArtist) {
    JsonDto<ArtistShopDto> jDto = new JsonDto<ArtistShopDto>();
    ArtistShopDto artistShopDto = new ArtistShopDto();
    if (artistShopRepo.exists(idfArtist)) {
      ArtistShop artistShop = artistShopRepo.findOne(idfArtist);
      BeanUtil.copyProperties(artistShop, artistShopDto);
      jDto.setResultCode("S");
      jDto.setDataObject(artistShopDto);
    } else {
      jDto.setResultCode("F");
      jDto.setResultMessage("does not exist shop");
    }

    return jDto;
  }

}
