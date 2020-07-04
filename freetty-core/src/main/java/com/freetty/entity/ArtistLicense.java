package com.freetty.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the artist_license database table.
 * 
 */
@Entity
@Table(name="artist_license")
@NamedQuery(name="ArtistLicense.findAll", query="SELECT a FROM ArtistLicense a")
public class ArtistLicense implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idf_artist_license", unique=true, nullable=false)
	private int idfArtistLicense;

	@Column(length=1)
	private String confirm;

	@Column(length=30)
	private String name;

	@Column(name="reg_date", insertable=false, updatable=false, nullable=false)
	private Timestamp regDate;

	//bi-directional many-to-one association to Artist
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idf_artist", nullable=false)
	private Artist artist;

	public ArtistLicense() {
	}

	public int getIdfArtistLicense() {
		return this.idfArtistLicense;
	}

	public void setIdfArtistLicense(int idfArtistLicense) {
		this.idfArtistLicense = idfArtistLicense;
	}

	public String getConfirm() {
		return this.confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getRegDate() {
		return this.regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	public Artist getArtist() {
		return this.artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

}