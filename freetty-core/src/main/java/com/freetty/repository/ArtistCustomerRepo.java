package com.freetty.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freetty.entity.ArtistCustomer;
import com.freetty.entity.ArtistCustomerPK;

public interface ArtistCustomerRepo extends JpaRepository<ArtistCustomer, ArtistCustomerPK> {

  public Integer countByIdIdfArtist(int idfArtist);

  public List<ArtistCustomer> findByIdIdfArtist(int idfArtist);

}
