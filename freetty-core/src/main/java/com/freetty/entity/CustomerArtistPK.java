package com.freetty.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the customer_artist database table.
 * 
 */
@Embeddable
public class CustomerArtistPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="idf_customer", insertable=false, updatable=false, unique=true, nullable=false)
	private int idfCustomer;

	@Column(name="idf_artist", insertable=false, updatable=false, unique=true, nullable=false)
	private int idfArtist;

	public CustomerArtistPK() {
	}
	public int getIdfCustomer() {
		return this.idfCustomer;
	}
	public void setIdfCustomer(int idfCustomer) {
		this.idfCustomer = idfCustomer;
	}
	public int getIdfArtist() {
		return this.idfArtist;
	}
	public void setIdfArtist(int idfArtist) {
		this.idfArtist = idfArtist;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CustomerArtistPK)) {
			return false;
		}
		CustomerArtistPK castOther = (CustomerArtistPK)other;
		return 
			(this.idfCustomer == castOther.idfCustomer)
			&& (this.idfArtist == castOther.idfArtist);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idfCustomer;
		hash = hash * prime + this.idfArtist;
		
		return hash;
	}
}