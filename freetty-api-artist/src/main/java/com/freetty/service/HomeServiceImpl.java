package com.freetty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freetty.dto.HomeDto.SideMenuLobbyDto;
import com.freetty.dto.JsonDto;
import com.freetty.entity.Artist;
import com.freetty.repository.ArtistRepo;

@Service
@Transactional
public class HomeServiceImpl {

	@Autowired
	ArtistRepo artistRepo;

	public JsonDto<?> sideMenuLobby(Integer idfArtist) {
		JsonDto<SideMenuLobbyDto> jDto = new JsonDto<SideMenuLobbyDto>();
		SideMenuLobbyDto sideMenuLobbyDto = new SideMenuLobbyDto();
		if (artistRepo.exists(idfArtist)) {
			Artist artist = artistRepo.findOne(idfArtist);
			sideMenuLobbyDto.setIdfArtist(idfArtist);
			sideMenuLobbyDto.setName(artist.getName());
			sideMenuLobbyDto.setKindName(artist.getKindName());
			if (artist.getArtistShop() != null) {
				sideMenuLobbyDto.setShopName(artist.getArtistShop().getName());
			}
			jDto.setResultCode("S");
			jDto.setDataObject(sideMenuLobbyDto);
		} else {
			jDto.setResultCode("F");
			jDto.setResultMessage("does not exist artist");
		}
		return jDto;
	}

}
