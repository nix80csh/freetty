package com.freetty.dto;

import lombok.Data;

@Data
public class HomeDto {
	@Data
	public static class SideMenuLobbyDto {
		private Integer idfArtist;
		private String Name;
		private String kindName;
		private String shopName;
	}
}
