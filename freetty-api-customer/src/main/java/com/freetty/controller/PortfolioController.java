package com.freetty.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.freetty.dto.JsonDto;
import com.freetty.dto.PortfolioDto.PortfolioManageDto;
import com.freetty.dto.PortfolioDto.RegistBookmark;
import com.freetty.dto.PortfolioDto.RegistWishlist;
import com.freetty.service.PortfolioServiceImpl;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController extends BaseController {

	@Autowired
	PortfolioServiceImpl portfolioService;

	@RequestMapping(value = {
			"/readByKindName/{kindName}/{pageNum}/{pageSize}/{idfCustomer}" }, method = RequestMethod.GET)
	public ResponseEntity<?> readByKindName(@PathVariable String kindName, @PathVariable Integer pageNum,
			@PathVariable Integer pageSize, @PathVariable Integer idfCustomer) {
		JsonDto<PortfolioManageDto> jDto = portfolioService.readByKindName(kindName, pageNum, pageSize, idfCustomer);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/readDetailByIdfArtistPortfolio/{idfArtistPortfolio}/{idfCustomer}", method = RequestMethod.GET)
	public ResponseEntity<?> readDetailByIdfArtistPortfolio(@PathVariable Integer idfArtistPortfolio,
			@PathVariable Integer idfCustomer) {
		JsonDto<?> jDto = portfolioService.readDetailByIdfArtistPortfolio(idfArtistPortfolio, idfCustomer);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/registBookmark", method = RequestMethod.POST)
	public ResponseEntity<?> registBookmark(@RequestBody RegistBookmark registBookmark) {
		JsonDto<?> jDto = portfolioService.registBookmark(registBookmark);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/registWishlist", method = RequestMethod.POST)
	public ResponseEntity<?> registWishlist(@RequestBody RegistWishlist registWishlist) {
		JsonDto<?> jDto = portfolioService.registWishlist(registWishlist);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}

}
