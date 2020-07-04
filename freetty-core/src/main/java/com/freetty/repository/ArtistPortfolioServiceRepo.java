package com.freetty.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freetty.entity.ArtistPortfolioService;

public interface ArtistPortfolioServiceRepo extends JpaRepository<ArtistPortfolioService, Integer> {

	public ArtistPortfolioService findByArtistPortfolioIdfArtistPortfolioAndType(int idfArtistPortfolio,
			String serviceType);
	
	public void deleteByArtistPortfolioIdfArtistPortfolio(int idfArtistPortfolio);
	
	public int countByArtistPortfolioIdfArtistPortfolio(int idfArtistPortfolio);
	
	public List<ArtistPortfolioService> findByArtistPortfolioIdfArtistPortfolio(int idfArtistPortfolio);
	
}
