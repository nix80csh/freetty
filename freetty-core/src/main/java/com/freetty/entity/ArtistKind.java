package com.freetty.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the artist_kind database table.
 * 
 */
@Entity
@Table(name="artist_kind")
@NamedQuery(name="ArtistKind.findAll", query="SELECT a FROM ArtistKind a")
public class ArtistKind implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idf_artist_kind", unique=true, nullable=false)
	private int idfArtistKind;

	@Column(name="kind_name", length=25)
	private String kindName;

	@Column(name="license_name", length=25)
	private String licenseName;

	@Column(name="reg_date", insertable=false, updatable=false, nullable=false)
	private Timestamp regDate;

	//bi-directional many-to-one association to Artist
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idf_artist", nullable=false)
	private Artist artist;

	//bi-directional many-to-one association to LicenseKind
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idf_license_kind", nullable=false)
	private LicenseKind licenseKind;

	//bi-directional many-to-one association to ArtistPortfolio
	@OneToMany(mappedBy="artistKind")
	private List<ArtistPortfolio> artistPortfolios;

	public ArtistKind() {
	}

	public int getIdfArtistKind() {
		return this.idfArtistKind;
	}

	public void setIdfArtistKind(int idfArtistKind) {
		this.idfArtistKind = idfArtistKind;
	}

	public String getKindName() {
		return this.kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	public String getLicenseName() {
		return this.licenseName;
	}

	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}

	public Timestamp getRegDate() {
		return this.regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	public Artist getArtist() {
		return this.artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public LicenseKind getLicenseKind() {
		return this.licenseKind;
	}

	public void setLicenseKind(LicenseKind licenseKind) {
		this.licenseKind = licenseKind;
	}

	public List<ArtistPortfolio> getArtistPortfolios() {
		return this.artistPortfolios;
	}

	public void setArtistPortfolios(List<ArtistPortfolio> artistPortfolios) {
		this.artistPortfolios = artistPortfolios;
	}

	public ArtistPortfolio addArtistPortfolio(ArtistPortfolio artistPortfolio) {
		getArtistPortfolios().add(artistPortfolio);
		artistPortfolio.setArtistKind(this);

		return artistPortfolio;
	}

	public ArtistPortfolio removeArtistPortfolio(ArtistPortfolio artistPortfolio) {
		getArtistPortfolios().remove(artistPortfolio);
		artistPortfolio.setArtistKind(null);

		return artistPortfolio;
	}

}