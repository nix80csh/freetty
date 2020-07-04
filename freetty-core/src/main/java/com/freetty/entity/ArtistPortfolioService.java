package com.freetty.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the artist_portfolio_service database table.
 * 
 */
@Entity
@Table(name="artist_portfolio_service")
@NamedQuery(name="ArtistPortfolioService.findAll", query="SELECT a FROM ArtistPortfolioService a")
public class ArtistPortfolioService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idf_artist_portfolio_service", unique=true, nullable=false)
	private int idfArtistPortfolioService;

	@Column(name="lead_time")
	private int leadTime;

	@Column(length=30)
	private String name;

	private int price;

	@Column(name="reg_date", insertable=false, updatable=false, nullable=false)
	private Timestamp regDate;

	@Column(length=1)
	private String type;

	//bi-directional many-to-one association to ArtistPortfolio
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idf_artist_portfolio", nullable=false)
	private ArtistPortfolio artistPortfolio;

	public ArtistPortfolioService() {
	}

	public int getIdfArtistPortfolioService() {
		return this.idfArtistPortfolioService;
	}

	public void setIdfArtistPortfolioService(int idfArtistPortfolioService) {
		this.idfArtistPortfolioService = idfArtistPortfolioService;
	}

	public int getLeadTime() {
		return this.leadTime;
	}

	public void setLeadTime(int leadTime) {
		this.leadTime = leadTime;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Timestamp getRegDate() {
		return this.regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArtistPortfolio getArtistPortfolio() {
		return this.artistPortfolio;
	}

	public void setArtistPortfolio(ArtistPortfolio artistPortfolio) {
		this.artistPortfolio = artistPortfolio;
	}

}