package com.freetty.repository;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.freetty.entity.ArtistPortfolio;

public interface ArtistPortfolioRepo extends JpaRepository<ArtistPortfolio, Integer> {

  public Page<ArtistPortfolio> findByArtistIdfArtistAndIsDeployOrderByIdfArtistPortfolioDesc(
      int idfArtist, String isDeploy, Pageable pageable);

  public Page<ArtistPortfolio> findByArtistKindIdfArtistKindInOrderByIdfArtistPortfolioDesc(
      Collection<Integer> idfArtistKind, Pageable pageable);

}
