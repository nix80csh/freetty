package com.freetty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.freetty.dto.CustomerDto.sendInviteSMS;
import com.freetty.dto.JsonDto;
import com.freetty.service.CustomerServiceImpl;

@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController {

	@Autowired
	CustomerServiceImpl customerService;

	@RequestMapping(value = "/searchCustomerByMobile/{idfArtist}/{mobile}", method = RequestMethod.GET)
	public ResponseEntity<?> searchCustomerByMobile(@PathVariable Integer idfArtist, @PathVariable String mobile) {
		JsonDto<?> jDto = customerService.searchCustomerByMobile(idfArtist, mobile);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/sendInvite", method = RequestMethod.POST)
	public ResponseEntity<?> sendInvite(@RequestBody sendInviteSMS sendInviteSMSDto) {
		JsonDto<?> jDto = customerService.sendInvite(sendInviteSMSDto);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/readAllByIdfArtist/{idfArtist}", method = RequestMethod.GET)
	public ResponseEntity<?> readAllByIdfArtist(@PathVariable Integer idfArtist) {

		JsonDto<?> jDto = customerService.readAllByIdfArtist(idfArtist);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/checkMobileValidation/{idfArtist}", method = RequestMethod.GET)
	public ResponseEntity<?> checkMobileValidation(@PathVariable Integer idfArtist) {

		JsonDto<?> jDto = customerService.checkMobileValidation(idfArtist);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}

}
