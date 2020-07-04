package com.freetty.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.freetty.common.EncryptionSha256Util;
import com.freetty.common.ImageUtil;
import com.freetty.dto.AuthDto;
import com.freetty.dto.AuthDto.ModifyPasswordDto;
import com.freetty.dto.AuthDto.ValidateSMSCodeDto;
import com.freetty.dto.EncryptionDto;
import com.freetty.dto.JsonDto;
import com.freetty.service.AuthServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {
	
	
	@Autowired
	AuthServiceImpl authService;

	@Autowired
	ImageUtil imageUtil;

	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public ResponseEntity<?> signin(@RequestBody @Valid AuthDto authDto, HttpServletResponse res, Device device) {
		JsonDto<?> jDto = authService.signin(authDto, res, device);
		logger.info(device.getDevicePlatform().name());		
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<?> signup(@RequestBody @Valid AuthDto authDto) {
		JsonDto<?> jDto = authService.signup(authDto);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
	public ResponseEntity<?> modifyPassword(@RequestBody @Valid ModifyPasswordDto modifyPasswordDto) {
		JsonDto<?> jDto = authService.modifyPassword(modifyPasswordDto);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sendPasswordToEmail", method = RequestMethod.POST)
	public ResponseEntity<?> sendPasswordToEmail(String email) {
		JsonDto<?> jDto = authService.sendPasswordToEmail(email);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}
	
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

	@RequestMapping(value = "/encryptionSha256", method = RequestMethod.POST)
	public ResponseEntity<?> encryptionSha256(@RequestBody EncryptionDto encryptionDto){
		String encStr = EncryptionSha256Util.getEncSHA256(encryptionDto.getBeforeEncryt());
		encryptionDto.setAfterEncryt(encStr);		
		return new ResponseEntity<Object>(encryptionDto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/removeArtist", method = RequestMethod.POST)
	public ResponseEntity<?> removeArtist(Integer idfArtist) {
		JsonDto<?> jDto = authService.removeArtist(idfArtist);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}
	
	
	
	@Secured("IS_AUTHENTICATED_FULLY")
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ResponseEntity<?> authTest() {
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

	@RequestMapping(value = "/uploadTest", method = RequestMethod.POST)
	public List<String> uploadTest(MultipartFile[] images) {
		return imageUtil.uploadImage(bucketName, "profile", "1", images);
	}

}
