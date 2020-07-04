package com.freetty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.freetty.dto.CustomerDto;
import com.freetty.dto.CustomerDto.SaveProfileImageDto;
import com.freetty.dto.JsonDto;
import com.freetty.service.CustomerServiceImpl;

@RestController
@RequestMapping("/customer")
public class CustomerController extends BaseController {
	
	@Autowired
	CustomerServiceImpl customerService;

	@RequestMapping(value = "/readProfileByIdf/{idfCustomer}", method = RequestMethod.GET)
	public ResponseEntity<?> readProfileByIdf(@PathVariable Integer idfCustomer) {
		JsonDto<?> jDto = customerService.readProfileByIdf(idfCustomer);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/registProfile", method = RequestMethod.POST)
	public ResponseEntity<?> registProfile(@RequestBody CustomerDto customerDto) {
		JsonDto<?> jDto = customerService.registProfile(customerDto);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/registProfileImage", method = RequestMethod.POST)
	public ResponseEntity<?> registProfileImage(SaveProfileImageDto saveProfileImageDto) {
		JsonDto<?> jDto = customerService.registProfileImage(saveProfileImageDto);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/checkMobileValidation/{idfCustomer}", method = RequestMethod.GET)
    public ResponseEntity<?> checkMobileValidation(@PathVariable Integer idfCustomer) {

        JsonDto<?> jDto = customerService.checkMobileValidation(idfCustomer);
        return new ResponseEntity<>(jDto, HttpStatus.OK);
    }
	

}
