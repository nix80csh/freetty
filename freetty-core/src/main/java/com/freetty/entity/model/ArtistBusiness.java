package com.freetty.entity.model;

import lombok.Data;

@Data
public class ArtistBusiness {	
	private String kindName;
	private String majorServiceDescription;
	private byte serviceDistance;
	
	public ArtistBusiness(String kindName, String majorServiceDescription, byte serviceDistance) {
		super();
		this.kindName = kindName;
		this.majorServiceDescription = majorServiceDescription;
		this.serviceDistance = serviceDistance;
	}	
	
}