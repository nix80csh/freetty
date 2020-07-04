package com.freetty.service;


import org.codehaus.plexus.component.annotations.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.freetty.common.EncryptionSha256Util;
import com.freetty.config.ApplicationConfig;
import com.freetty.entity.Artist;
import com.freetty.repository.ArtistRepo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationConfig.class)
@WebAppConfiguration
public class AccountServiceTest {	
	
	@Autowired ArtistRepo artistRepo;
	
	private Artist artist;
	@Before
	public void setup(){
		artist = artistRepo.findByEmail("chosh@freetty.com");
	}
	
	@Test
	public void sha256PasswordEncodeTest(){
		String sha256PasswordEncodeValue = EncryptionSha256Util.getEncSHA256("585Z2GUT17");
		System.out.println(sha256PasswordEncodeValue.length());
		System.out.println(sha256PasswordEncodeValue);
	}
	
}
