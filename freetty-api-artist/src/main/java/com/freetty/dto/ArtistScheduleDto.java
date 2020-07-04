package com.freetty.dto;

import lombok.Data;

@Data
public class ArtistScheduleDto {
	private Integer idfArtist;
	private String mondayStartTime;
	private String mondayEndTime;
	private String tusedayStartTime;
	private String tusedayEndTime;
	private String wednesdayStartTime;
	private String wednesdayEndTime;
	private String thursdayStartDate;
	private String thursdayEndDate;
	private String fridayStartTime;
	private String fridayEndTime;
	private String saturdayStartTime;
	private String saturdayEndTime;
	private String sundayStartTime;
	private String sundayEndTime;

}
