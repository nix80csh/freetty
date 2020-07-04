package com.freetty.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.freetty.dto.AuthDto;
import com.freetty.dto.JsonDto;
import com.freetty.dto.AuthDto.ModifyPasswordDto;
import com.freetty.dto.AuthDto.ValidateSMSCodeDto;
import com.freetty.service.AuthServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {
	
	@Autowired AuthServiceImpl authService;

	@Secured("IS_AUTHENTICATED_FULLY")
	@RequestMapping(value = "/sendSMSCode", method = RequestMethod.POST)
	public ResponseEntity<?> sendSMSCode(@RequestBody ValidateSMSCodeDto authDtoValidateSMSCode) {
		JsonDto<?> jDto = authService.sendSMSCode(authDtoValidateSMSCode);
		return new ResponseEntity<Object>(jDto, HttpStatus.OK);
	}
	
	@Secured("IS_AUTHENTICATED_FULLY")
	@RequestMapping(value = "/validateSMSCode", method = RequestMethod.POST)
	public ResponseEntity<?> validateSMSCode(@RequestBody ValidateSMSCodeDto authDtoValidateSMSCode) {
		JsonDto<?> jDto = authService.validateSMSCode(authDtoValidateSMSCode);
		return new ResponseEntity<Object>(jDto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/ftSignup", method = RequestMethod.POST)
	public ResponseEntity<?> ftSignup(@RequestBody @Valid AuthDto authDto, HttpServletResponse res) {			
		JsonDto<AuthDto> jDto = authService.ftSignup(authDto, res);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/ftSignin", method = RequestMethod.POST)
	public ResponseEntity<?> ftSignin(@RequestBody @Valid AuthDto authDto, HttpServletResponse res) {			
		JsonDto<AuthDto> jDto = authService.ftSignin(authDto, res);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/fbSignin", method = RequestMethod.POST)
	public ResponseEntity<?> fbSignin(String accessToken, HttpServletResponse res) {			
		JsonDto<AuthDto> jDto = authService.fbSignin(accessToken, res);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sendPasswordToEmail", method = RequestMethod.POST)
	public ResponseEntity<?> sendPasswordToEmail(String email) {
		JsonDto<?> jDto = authService.sendPasswordToEmail(email);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
	public ResponseEntity<?> modifyPassword(@RequestBody @Valid ModifyPasswordDto modifyPasswordDto) {
		JsonDto<?> jDto = authService.modifyPassword(modifyPasswordDto);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/removeCustomer", method = RequestMethod.POST)
	public ResponseEntity<?> removeCustomer(Integer idfCustomer) {
		JsonDto<?> jDto = authService.removeCustomer(idfCustomer);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}
	
	

}
