package com.freetty.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the artist_customer database table.
 * 
 */
@Embeddable
public class ArtistCustomerPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="idf_artist", insertable=false, updatable=false, unique=true, nullable=false)
	private int idfArtist;

	@Column(name="idf_customer", insertable=false, updatable=false, unique=true, nullable=false)
	private int idfCustomer;

	public ArtistCustomerPK() {
	}
	public int getIdfArtist() {
		return this.idfArtist;
	}
	public void setIdfArtist(int idfArtist) {
		this.idfArtist = idfArtist;
	}
	public int getIdfCustomer() {
		return this.idfCustomer;
	}
	public void setIdfCustomer(int idfCustomer) {
		this.idfCustomer = idfCustomer;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ArtistCustomerPK)) {
			return false;
		}
		ArtistCustomerPK castOther = (ArtistCustomerPK)other;
		return 
			(this.idfArtist == castOther.idfArtist)
			&& (this.idfCustomer == castOther.idfCustomer);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idfArtist;
		hash = hash * prime + this.idfCustomer;
		
		return hash;
	}
}