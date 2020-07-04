package com.freetty.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the wishlist_portfolio database table.
 * 
 */
@Entity
@Table(name="wishlist_portfolio")
@NamedQuery(name="WishlistPortfolio.findAll", query="SELECT w FROM WishlistPortfolio w")
public class WishlistPortfolio implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private WishlistPortfolioPK id;

	@Column(name="reg_date", insertable=false, updatable=false)
	private Timestamp regDate;

	//bi-directional many-to-one association to ArtistPortfolio
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idf_artist_portfolio", nullable=false, insertable=false, updatable=false)
	private ArtistPortfolio artistPortfolio;

	//bi-directional many-to-one association to Customer
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idf_customer", nullable=false, insertable=false, updatable=false)
	private Customer customer;

	public WishlistPortfolio() {
	}

	public WishlistPortfolioPK getId() {
		return this.id;
	}

	public void setId(WishlistPortfolioPK id) {
		this.id = id;
	}

	public Timestamp getRegDate() {
		return this.regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	public ArtistPortfolio getArtistPortfolio() {
		return this.artistPortfolio;
	}

	public void setArtistPortfolio(ArtistPortfolio artistPortfolio) {
		this.artistPortfolio = artistPortfolio;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}