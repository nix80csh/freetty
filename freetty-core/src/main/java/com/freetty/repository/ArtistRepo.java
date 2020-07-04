package com.freetty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.freetty.entity.Artist;
import com.freetty.entity.model.ArtistBusiness;

public interface ArtistRepo extends JpaRepository<Artist, Integer> {
	
	public Artist findByEmail(String email);

	@Query("select a.kindName from Artist a where a.idfArtist = ?1")
	public String findKindNameByIdfArtist(int idfArtist);

	@Query("select new com.freetty.entity.model.ArtistBusiness"
			+ "(a.kindName, a.majorServiceDescription, a.serviceDistance) "
			+ "from Artist a where a.idfArtist = ?1")
	public ArtistBusiness findArtistBusinessByIdfArtist(int idfArtist);
}
