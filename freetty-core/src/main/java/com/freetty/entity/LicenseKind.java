package com.freetty.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the license_kind database table.
 * 
 */
@Entity
@Table(name="license_kind")
@NamedQuery(name="LicenseKind.findAll", query="SELECT l FROM LicenseKind l")
public class LicenseKind implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idf_license_kind", unique=true, nullable=false)
	private int idfLicenseKind;

	@Column(name="kind_name", nullable=false, length=25)
	private String kindName;

	@Column(name="license_name", nullable=false, length=25)
	private String licenseName;

	@Column(name="reg_date", insertable=false, updatable=false, nullable=false)
	private Timestamp regDate;

	//bi-directional many-to-one association to ArtistKind
	@OneToMany(mappedBy="licenseKind")
	private List<ArtistKind> artistKinds;

	public LicenseKind() {
	}

	public int getIdfLicenseKind() {
		return this.idfLicenseKind;
	}

	public void setIdfLicenseKind(int idfLicenseKind) {
		this.idfLicenseKind = idfLicenseKind;
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

	public List<ArtistKind> getArtistKinds() {
		return this.artistKinds;
	}

	public void setArtistKinds(List<ArtistKind> artistKinds) {
		this.artistKinds = artistKinds;
	}

	public ArtistKind addArtistKind(ArtistKind artistKind) {
		getArtistKinds().add(artistKind);
		artistKind.setLicenseKind(this);

		return artistKind;
	}

	public ArtistKind removeArtistKind(ArtistKind artistKind) {
		getArtistKinds().remove(artistKind);
		artistKind.setLicenseKind(null);

		return artistKind;
	}

}