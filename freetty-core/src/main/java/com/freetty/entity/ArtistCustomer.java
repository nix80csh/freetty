package com.freetty.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the artist_customer database table.
 * 
 */
@Entity
@Table(name="artist_customer")
@NamedQuery(name="ArtistCustomer.findAll", query="SELECT a FROM ArtistCustomer a")
public class ArtistCustomer implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ArtistCustomerPK id;

	@Column(length=100)
	private String description;

	@Column(name="discount_rate")
	private byte discountRate;

	@Column(name="reg_date", insertable=false, updatable=false, nullable=false)
	private Timestamp regDate;

	//bi-directional many-to-one association to Artist
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idf_artist", nullable=false, insertable=false, updatable=false)
	private Artist artist;

	//bi-directional many-to-one association to Customer
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idf_customer", nullable=false, insertable=false, updatable=false)
	private Customer customer;

	public ArtistCustomer() {
	}

	public ArtistCustomerPK getId() {
		return this.id;
	}

	public void setId(ArtistCustomerPK id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte getDiscountRate() {
		return this.discountRate;
	}

	public void setDiscountRate(byte discountRate) {
		this.discountRate = discountRate;
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

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}