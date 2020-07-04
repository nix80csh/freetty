package com.freetty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.freetty.dto.JsonDto;
import com.freetty.dto.PortfolioDto.ModifyIsDeployDto;
import com.freetty.dto.PortfolioDto.PortfolioImageDto;
import com.freetty.dto.PortfolioDto.RegistPortfolioDto;
import com.freetty.service.PortfolioServiceImpl;

@Controller
@RequestMapping("/portfolio")
public class PortfolioController extends BaseController {

	@Autowired
	PortfolioServiceImpl portfolioService;

	@RequestMapping(value = "/registPortfolioImage", method = RequestMethod.POST)
	public ResponseEntity<?> registPortfolioImage(PortfolioImageDto portfolioImageDto) {
		JsonDto<?> jDto = portfolioService.registPortfolioImage(portfolioImageDto);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/registPortfolio", method = RequestMethod.POST)
	public ResponseEntity<?> registPortfolio(@RequestBody RegistPortfolioDto registPortfolioDto) {
		JsonDto<?> jDto = portfolioService.registPortfolio(registPortfolioDto);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}	

	@RequestMapping(value = "/checkToConfirmedLicense/{idfArtist}/{confirm}", method = RequestMethod.GET)
	public ResponseEntity<?> checkToConfirmedLicense(@PathVariable Integer idfArtist, @PathVariable String confirm) {
		JsonDto<?> jDto = portfolioService.checkToConfirmedLicense(idfArtist, confirm);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/readAllByIdfArtist/{idfArtist}/{pageNum}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<?> readAllByIdfArtist(@PathVariable Integer idfArtist, @PathVariable Integer pageNum,
			@PathVariable Integer pageSize) {
		JsonDto<?> jDto = portfolioService.readAllByIdfArtist(idfArtist, pageNum, pageSize);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/readByIdfArtistPortfolio1/{idfArtistPortfolio}", method = RequestMethod.GET)
	public ResponseEntity<?> readByIdfArtistPortfolio1(@PathVariable Integer idfArtistPortfolio) {
		JsonDto<?> jDto = portfolioService.readByIdfArtistPortfolio1(idfArtistPortfolio);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/readByIdfArtistPortfolio2/{idfArtistPortfolio}", method = RequestMethod.GET)
	public ResponseEntity<?> readByIdfArtistPortfolio2(@PathVariable Integer idfArtistPortfolio) {
		JsonDto<?> jDto = portfolioService.readByIdfArtistPortfolio2(idfArtistPortfolio);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/deleteByIdfArtistPortfolio", method = RequestMethod.POST)
	public ResponseEntity<?> deleteByIdfArtistPortfolio(Integer idfArtistPortfolio) {
		JsonDto<?> jDto = portfolioService.deleteByIdfArtistPortfolio(idfArtistPortfolio);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/deleteImage", method = RequestMethod.POST)
	public ResponseEntity<?> deleteImage(String imageName) {
		JsonDto<?> jDto = portfolioService.deleteImage(imageName);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/modifyIsDeploy", method = RequestMethod.POST)
	public ResponseEntity<?> modifyIsDeploy(@RequestBody ModifyIsDeployDto modifyIsDeployDto) {
		JsonDto<?> jDto = portfolioService.modifyIsDeploy(modifyIsDeployDto);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/readDetailByIdfArtistPortfolio/{idfArtistPortfolio}", method = RequestMethod.GET)
	public ResponseEntity<?> readDetailByIdfArtistPortfolio(@PathVariable Integer idfArtistPortfolio) {
		JsonDto<?> jDto = portfolioService.readDetailByIdfArtistPortfolio(idfArtistPortfolio);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/readServiceDistanceByIdfArtist/{idfArtist}", method = RequestMethod.GET)
	public ResponseEntity<?> readServiceDistanceByIdfArtist(@PathVariable Integer idfArtist) {
		JsonDto<?> jDto = portfolioService.readServiceDistanceByIdfArtist(idfArtist);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}
	
}
