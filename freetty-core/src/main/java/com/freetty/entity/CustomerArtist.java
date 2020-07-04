package com.freetty.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the customer_artist database table.
 * 
 */
@Entity
@Table(name="customer_artist")
@NamedQuery(name="CustomerArtist.findAll", query="SELECT c FROM CustomerArtist c")
public class CustomerArtist implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CustomerArtistPK id;

	@Column(name="reg_date", insertable=false, updatable=false)
	private Timestamp regDate;

	//bi-directional many-to-one association to Artist
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idf_artist", nullable=false, insertable=false, updatable=false)
	private Artist artist;

	//bi-directional many-to-one association to Customer
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idf_customer", nullable=false, insertable=false, updatable=false)
	private Customer customer;

	public CustomerArtist() {
	}

	public CustomerArtistPK getId() {
		return this.id;
	}

	public void setId(CustomerArtistPK id) {
		this.id = id;
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