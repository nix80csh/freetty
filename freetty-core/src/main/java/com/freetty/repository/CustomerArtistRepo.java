package com.freetty.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freetty.entity.CustomerArtist;
import com.freetty.entity.CustomerArtistPK;

public interface CustomerArtistRepo extends JpaRepository<CustomerArtist, CustomerArtistPK> {
	
	public Integer countByIdIdfArtist(int idfArtist);

}
