package com.freetty.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the artist_shop database table.
 * 
 */
@Entity
@Table(name="artist_shop")
@NamedQuery(name="ArtistShop.findAll", query="SELECT a FROM ArtistShop a")
public class ArtistShop implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="idf_artist", unique=true, nullable=false)
	private int idfArtist;

	@Column(length=100)
	private String addr1;

	@Column(length=100)
	private String addr2;

	@Column(length=30)
	private String name;

	@Column(length=11)
	private String phone;

	@Column(name="reg_date", insertable=false, updatable=false, nullable=false)
	private Timestamp regDate;

	//bi-directional one-to-one association to Artist
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idf_artist", nullable=false, insertable=false, updatable=false)
	private Artist artist;

	public ArtistShop() {
	}

	public int getIdfArtist() {
		return this.idfArtist;
	}

	public void setIdfArtist(int idfArtist) {
		this.idfArtist = idfArtist;
	}

	public String getAddr1() {
		return this.addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return this.addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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