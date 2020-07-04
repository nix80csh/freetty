package com.freetty.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the artist_schedule database table.
 * 
 */
@Entity
@Table(name="artist_schedule")
@NamedQuery(name="ArtistSchedule.findAll", query="SELECT a FROM ArtistSchedule a")
public class ArtistSchedule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="idf_artist", unique=true, nullable=false)
	private int idfArtist;

	@Column(name="friday_end_time", length=5)
	private String fridayEndTime;

	@Column(name="friday_start_time", length=5)
	private String fridayStartTime;

	@Column(name="monday_end_time", length=5)
	private String mondayEndTime;

	@Column(name="monday_start_time", length=5)
	private String mondayStartTime;

	@Column(name="reg_date", insertable=false, updatable=false, nullable=false)
	private Timestamp regDate;

	@Column(name="saturday_end_time", length=5)
	private String saturdayEndTime;

	@Column(name="saturday_start_time", length=5)
	private String saturdayStartTime;

	@Column(name="sunday_end_time", length=5)
	private String sundayEndTime;

	@Column(name="sunday_start_time", length=5)
	private String sundayStartTime;

	@Column(name="thursday_end_date", length=5)
	private String thursdayEndDate;

	@Column(name="thursday_start_date", length=5)
	private String thursdayStartDate;

	@Column(name="tuseday_end_time", length=5)
	private String tusedayEndTime;

	@Column(name="tuseday_start_time", length=5)
	private String tusedayStartTime;

	@Column(name="wednesday_end_time", length=5)
	private String wednesdayEndTime;

	@Column(name="wednesday_start_time", length=5)
	private String wednesdayStartTime;

	//bi-directional one-to-one association to Artist
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idf_artist", nullable=false, insertable=false, updatable=false)
	private Artist artist;

	public ArtistSchedule() {
	}

	public int getIdfArtist() {
		return this.idfArtist;
	}

	public void setIdfArtist(int idfArtist) {
		this.idfArtist = idfArtist;
	}

	public String getFridayEndTime() {
		return this.fridayEndTime;
	}

	public void setFridayEndTime(String fridayEndTime) {
		this.fridayEndTime = fridayEndTime;
	}

	public String getFridayStartTime() {
		return this.fridayStartTime;
	}

	public void setFridayStartTime(String fridayStartTime) {
		this.fridayStartTime = fridayStartTime;
	}

	public String getMondayEndTime() {
		return this.mondayEndTime;
	}

	public void setMondayEndTime(String mondayEndTime) {
		this.mondayEndTime = mondayEndTime;
	}

	public String getMondayStartTime() {
		return this.mondayStartTime;
	}

	public void setMondayStartTime(String mondayStartTime) {
		this.mondayStartTime = mondayStartTime;
	}

	public Timestamp getRegDate() {
		return this.regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	public String getSaturdayEndTime() {
		return this.saturdayEndTime;
	}

	public void setSaturdayEndTime(String saturdayEndTime) {
		this.saturdayEndTime = saturdayEndTime;
	}

	public String getSaturdayStartTime() {
		return this.saturdayStartTime;
	}

	public void setSaturdayStartTime(String saturdayStartTime) {
		this.saturdayStartTime = saturdayStartTime;
	}

	public String getSundayEndTime() {
		return this.sundayEndTime;
	}

	public void setSundayEndTime(String sundayEndTime) {
		this.sundayEndTime = sundayEndTime;
	}

	public String getSundayStartTime() {
		return this.sundayStartTime;
	}

	public void setSundayStartTime(String sundayStartTime) {
		this.sundayStartTime = sundayStartTime;
	}

	public String getThursdayEndDate() {
		return this.thursdayEndDate;
	}

	public void setThursdayEndDate(String thursdayEndDate) {
		this.thursdayEndDate = thursdayEndDate;
	}

	public String getThursdayStartDate() {
		return this.thursdayStartDate;
	}

	public void setThursdayStartDate(String thursdayStartDate) {
		this.thursdayStartDate = thursdayStartDate;
	}

	public String getTusedayEndTime() {
		return this.tusedayEndTime;
	}

	public void setTusedayEndTime(String tusedayEndTime) {
		this.tusedayEndTime = tusedayEndTime;
	}

	public String getTusedayStartTime() {
		return this.tusedayStartTime;
	}

	public void setTusedayStartTime(String tusedayStartTime) {
		this.tusedayStartTime = tusedayStartTime;
	}

	public String getWednesdayEndTime() {
		return this.wednesdayEndTime;
	}

	public void setWednesdayEndTime(String wednesdayEndTime) {
		this.wednesdayEndTime = wednesdayEndTime;
	}

	public String getWednesdayStartTime() {
		return this.wednesdayStartTime;
	}

	public void setWednesdayStartTime(String wednesdayStartTime) {
		this.wednesdayStartTime = wednesdayStartTime;
	}

	public Artist getArtist() {
		return this.artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

}