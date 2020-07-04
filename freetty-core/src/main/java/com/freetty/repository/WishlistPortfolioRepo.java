package com.freetty.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freetty.entity.WishlistPortfolio;
import com.freetty.entity.WishlistPortfolioPK;

public interface WishlistPortfolioRepo extends JpaRepository<WishlistPortfolio, WishlistPortfolioPK>{

	public Integer countByIdIdfArtistPortfolio(int idfArtistPortfolio);
}
