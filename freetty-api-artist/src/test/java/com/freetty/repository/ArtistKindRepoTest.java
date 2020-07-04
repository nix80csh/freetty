package com.freetty.repository;
import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.freetty.config.ApplicationConfig;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationConfig.class)
@Transactional
public class ArtistKindRepoTest {

	@Autowired
	ArtistKindRepo artistKindRepo;
	
	@Before
	public void setup() {

	}

	@Test	
	public void deleteByIdfArtist() {
		artistKindRepo.deleteByArtistIdfArtist(19);
	}

}
