package com.freetty.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freetty.entity.ArtistLicense;

public interface ArtistLicenseRepo extends JpaRepository<ArtistLicense, Integer>{

//	@Query("select al.* from ArtistLicense al where al.id.idfArtist = ?1")
//	public List<ArtistLicense> findByIdfArtist(int idfArtist);
	
	public List<ArtistLicense> findByArtistIdfArtist(int idfArtist);
	public int countByArtistIdfArtistAndConfirm(int idfArtist, String confirm);
	public void deleteByArtistIdfArtistAndName(int idfArtist, String name);
	public int countByArtistIdfArtist(int idfArtist);
	
}
