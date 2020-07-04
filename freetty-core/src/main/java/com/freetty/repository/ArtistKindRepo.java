package com.freetty.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freetty.entity.ArtistKind;

public interface ArtistKindRepo extends JpaRepository<ArtistKind, Integer> {

  public int deleteByArtistIdfArtist(int idfArtist);

  public int deleteByArtistIdfArtistAndLicenseName(Integer idfArtist, String licenseName);

  public List<ArtistKind> findByArtistIdfArtist(int idfArtist);

  public List<ArtistKind> findByLicenseKindIdfLicenseKindIn(Collection<Integer> idfLicenseKind);

}
