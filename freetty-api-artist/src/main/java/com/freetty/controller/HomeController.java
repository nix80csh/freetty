package com.freetty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.freetty.dto.JsonDto;
import com.freetty.service.HomeServiceImpl;
 
@RestController
@RequestMapping("/home")
public class HomeController extends BaseController {	
	
	@Autowired HomeServiceImpl homeService;
	
	@RequestMapping(value = "/sideMenuLobby/{idfArtist}", method = RequestMethod.GET)
	public ResponseEntity<?> sideMenuLobby(@PathVariable Integer idfArtist) {
		JsonDto<?> jDto = homeService.sideMenuLobby(idfArtist);
		return new ResponseEntity<>(jDto, HttpStatus.OK);
	}

	
			
}
