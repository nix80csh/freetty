package com.freetty.dto;

import java.util.List;

import lombok.Data;

@Data
public class ArtistCustomerDto {

	@Data
	public static class RegistArtistCustomerDto {
		private Integer idfArtist;
		private Integer idfCustomer;
		private byte discountRate;
		private String description;
	}
	
	@Data
	public static class ArtistCustomerManagerDto {		
		private Integer countArtistCustomer;
		private List<CustomerDto> customerList;
	}
	

}
