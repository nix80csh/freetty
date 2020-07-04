package com.freetty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.freetty.dto.ArtistLicenseDto;
import com.freetty.dto.ArtistShopDto;
import com.freetty.dto.BusinessDto.ArtistKindDto;
import com.freetty.dto.BusinessDto.BusinessListDto;
import com.freetty.dto.BusinessDto.ConfirmLicenseDto;
import com.freetty.dto.BusinessDto.RegistMajorServiceDto;
import com.freetty.dto.BusinessDto.RegistServiceDistanceDto;
import com.freetty.dto.BusinessDto.SaveLicenseImageDto;
import com.freetty.dto.BusinessDto.SelectArtistKindDto;
import com.freetty.dto.JsonDto;
import com.freetty.service.BusinessServiceImpl;

@Controller
@RequestMapping("/biz")
public class BusinessController extends BaseController {

	@Autowired
	BusinessServiceImpl businessService;

	@RequestMapping(value = "/readLicenseByIdfArtist/{idfArtist}", method = RequestMethod.GET)
	public ResponseEntity<?> readLicenseByIdfArtist(@PathVariable Integer idfArtist) {
		JsonDto<?> jDto = businessService.readLicenseByIdfArtist(idfArtist);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/registLicenseImage", method = RequestMethod.POST)
	public ResponseEntity<?> registLicenseImage(SaveLicenseImageDto saveLicenseImageDto) {
		JsonDto<?> jDto = businessService.registLicenseImage(saveLicenseImageDto);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/selectArtistKind", method = RequestMethod.POST)
	public ResponseEntity<?> selectArtistKind(@RequestBody SelectArtistKindDto selectArtistKindDto) {
		JsonDto<?> jDto = businessService.selectArtistKind(selectArtistKindDto);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/registMajorService", method = RequestMethod.POST)
	public ResponseEntity<?> registMajorService(@RequestBody RegistMajorServiceDto registMajorServiceDto) {
		JsonDto<?> jDto = businessService.registMajorService(registMajorServiceDto);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/registServiceDistance", method = RequestMethod.POST)
	public ResponseEntity<?> registServiceDistance(@RequestBody RegistServiceDistanceDto registServiceDistanceDto) {
		JsonDto<?> jDto = businessService.registServiceDistance(registServiceDistanceDto);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/confirmLicense", method = RequestMethod.POST)
	public ResponseEntity<?> confirmLicense(@RequestBody ConfirmLicenseDto confirmLicenseDto) {
		JsonDto<?> jDto = businessService.confirmLicense(confirmLicenseDto);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/readKindByIdfArtist/{idfArtist}", method = RequestMethod.GET)
	public ResponseEntity<?> readKindByIdfArtist(@PathVariable Integer idfArtist) {
		JsonDto<?> jDto = businessService.readKindByIdfArtist(idfArtist);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/readBusinessListByIdfArtist/{idfArtist}", method = RequestMethod.GET)
	public ResponseEntity<?> readBusinessListByIdfArtist(@PathVariable Integer idfArtist) {
		JsonDto<?> jDto = businessService.readBusinessListByIdfArtist(idfArtist);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/readArtistShopByIdfArtist/{idfArtist}", method = RequestMethod.GET)
	public ResponseEntity<?> readArtistShopByIdfArtist(@PathVariable Integer idfArtist) {
		JsonDto<?> jDto = businessService.readArtistShopByIdfArtist(idfArtist);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}
	
}
