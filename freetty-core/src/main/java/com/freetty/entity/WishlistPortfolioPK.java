package com.freetty.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the wishlist_portfolio database table.
 * 
 */
@Embeddable
public class WishlistPortfolioPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="idf_customer", insertable=false, updatable=false, unique=true, nullable=false)
	private int idfCustomer;

	@Column(name="idf_artist_portfolio", insertable=false, updatable=false, unique=true, nullable=false)
	private int idfArtistPortfolio;

	public WishlistPortfolioPK() {
	}
	public int getIdfCustomer() {
		return this.idfCustomer;
	}
	public void setIdfCustomer(int idfCustomer) {
		this.idfCustomer = idfCustomer;
	}
	public int getIdfArtistPortfolio() {
		return this.idfArtistPortfolio;
	}
	public void setIdfArtistPortfolio(int idfArtistPortfolio) {
		this.idfArtistPortfolio = idfArtistPortfolio;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof WishlistPortfolioPK)) {
			return false;
		}
		WishlistPortfolioPK castOther = (WishlistPortfolioPK)other;
		return 
			(this.idfCustomer == castOther.idfCustomer)
			&& (this.idfArtistPortfolio == castOther.idfArtistPortfolio);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idfCustomer;
		hash = hash * prime + this.idfArtistPortfolio;
		
		return hash;
	}
}