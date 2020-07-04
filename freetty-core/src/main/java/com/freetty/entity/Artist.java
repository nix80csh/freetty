package com.freetty.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the artist database table.
 * 
 */
@Entity
@Table(name="artist")
@NamedQuery(name="Artist.findAll", query="SELECT a FROM Artist a")
public class Artist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idf_artist", unique=true, nullable=false)
	private int idfArtist;

	@Column(length=8)
	private String birthday;

	@Column(nullable=false, length=45)
	private String email;

	@Column(length=1)
	private String gender;

	@Column(length=300)
	private String introduce;

	@Column(name="kind_name", length=25)
	private String kindName;

	@Column(name="language_chi", length=1)
	private String languageChi;

	@Column(name="language_eng", length=1)
	private String languageEng;

	@Column(name="language_jap", length=1)
	private String languageJap;

	@Column(name="language_sign", length=1)
	private String languageSign;

	@Column(name="major_service_description", length=300)
	private String majorServiceDescription;

	@Column(length=11)
	private String mobile;

	@Column(length=45)
	private String name;

	@Column(length=45)
	private String nickname;

	@Column(nullable=false, length=64)
	private String password;

	@Column(name="reg_date", insertable=false, updatable=false, nullable=false)
	private Timestamp regDate;

	@Column(name="reservation_policy", length=1)
	private String reservationPolicy;

	@Column(name="service_distance")
	private byte serviceDistance;

	//bi-directional many-to-one association to ArtistCustomer
	@OneToMany(mappedBy="artist")
	private List<ArtistCustomer> artistCustomers;

	//bi-directional many-to-one association to ArtistKind
	@OneToMany(mappedBy="artist")
	private List<ArtistKind> artistKinds;

	//bi-directional many-to-one association to ArtistLicense
	@OneToMany(mappedBy="artist")
	private List<ArtistLicense> artistLicenses;

	//bi-directional many-to-one association to ArtistPortfolio
	@OneToMany(mappedBy="artist")
	private List<ArtistPortfolio> artistPortfolios;

	//bi-directional one-to-one association to ArtistSchedule
	@OneToOne(mappedBy="artist", fetch=FetchType.LAZY)
	private ArtistSchedule artistSchedule;

	//bi-directional one-to-one association to ArtistShop
	@OneToOne(mappedBy="artist", fetch=FetchType.LAZY)
	private ArtistShop artistShop;

	//bi-directional many-to-one association to CustomerArtist
	@OneToMany(mappedBy="artist")
	private List<CustomerArtist> customerArtists;

	public Artist() {
	}

	public int getIdfArtist() {
		return this.idfArtist;
	}

	public void setIdfArtist(int idfArtist) {
		this.idfArtist = idfArtist;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getKindName() {
		return this.kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	public String getLanguageChi() {
		return this.languageChi;
	}

	public void setLanguageChi(String languageChi) {
		this.languageChi = languageChi;
	}

	public String getLanguageEng() {
		return this.languageEng;
	}

	public void setLanguageEng(String languageEng) {
		this.languageEng = languageEng;
	}

	public String getLanguageJap() {
		return this.languageJap;
	}

	public void setLanguageJap(String languageJap) {
		this.languageJap = languageJap;
	}

	public String getLanguageSign() {
		return this.languageSign;
	}

	public void setLanguageSign(String languageSign) {
		this.languageSign = languageSign;
	}

	public String getMajorServiceDescription() {
		return this.majorServiceDescription;
	}

	public void setMajorServiceDescription(String majorServiceDescription) {
		this.majorServiceDescription = majorServiceDescription;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getRegDate() {
		return this.regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	public String getReservationPolicy() {
		return this.reservationPolicy;
	}

	public void setReservationPolicy(String reservationPolicy) {
		this.reservationPolicy = reservationPolicy;
	}

	public byte getServiceDistance() {
		return this.serviceDistance;
	}

	public void setServiceDistance(byte serviceDistance) {
		this.serviceDistance = serviceDistance;
	}

	public List<ArtistCustomer> getArtistCustomers() {
		return this.artistCustomers;
	}

	public void setArtistCustomers(List<ArtistCustomer> artistCustomers) {
		this.artistCustomers = artistCustomers;
	}

	public ArtistCustomer addArtistCustomer(ArtistCustomer artistCustomer) {
		getArtistCustomers().add(artistCustomer);
		artistCustomer.setArtist(this);

		return artistCustomer;
	}

	public ArtistCustomer removeArtistCustomer(ArtistCustomer artistCustomer) {
		getArtistCustomers().remove(artistCustomer);
		artistCustomer.setArtist(null);

		return artistCustomer;
	}

	public List<ArtistKind> getArtistKinds() {
		return this.artistKinds;
	}

	public void setArtistKinds(List<ArtistKind> artistKinds) {
		this.artistKinds = artistKinds;
	}

	public ArtistKind addArtistKind(ArtistKind artistKind) {
		getArtistKinds().add(artistKind);
		artistKind.setArtist(this);

		return artistKind;
	}

	public ArtistKind removeArtistKind(ArtistKind artistKind) {
		getArtistKinds().remove(artistKind);
		artistKind.setArtist(null);

		return artistKind;
	}

	public List<ArtistLicense> getArtistLicenses() {
		return this.artistLicenses;
	}

	public void setArtistLicenses(List<ArtistLicense> artistLicenses) {
		this.artistLicenses = artistLicenses;
	}

	public ArtistLicense addArtistLicens(ArtistLicense artistLicens) {
		getArtistLicenses().add(artistLicens);
		artistLicens.setArtist(this);

		return artistLicens;
	}

	public ArtistLicense removeArtistLicens(ArtistLicense artistLicens) {
		getArtistLicenses().remove(artistLicens);
		artistLicens.setArtist(null);

		return artistLicens;
	}

	public List<ArtistPortfolio> getArtistPortfolios() {
		return this.artistPortfolios;
	}

	public void setArtistPortfolios(List<ArtistPortfolio> artistPortfolios) {
		this.artistPortfolios = artistPortfolios;
	}

	public ArtistPortfolio addArtistPortfolio(ArtistPortfolio artistPortfolio) {
		getArtistPortfolios().add(artistPortfolio);
		artistPortfolio.setArtist(this);

		return artistPortfolio;
	}

	public ArtistPortfolio removeArtistPortfolio(ArtistPortfolio artistPortfolio) {
		getArtistPortfolios().remove(artistPortfolio);
		artistPortfolio.setArtist(null);

		return artistPortfolio;
	}

	public ArtistSchedule getArtistSchedule() {
		return this.artistSchedule;
	}

	public void setArtistSchedule(ArtistSchedule artistSchedule) {
		this.artistSchedule = artistSchedule;
	}

	public ArtistShop getArtistShop() {
		return this.artistShop;
	}

	public void setArtistShop(ArtistShop artistShop) {
		this.artistShop = artistShop;
	}

	public List<CustomerArtist> getCustomerArtists() {
		return this.customerArtists;
	}

	public void setCustomerArtists(List<CustomerArtist> customerArtists) {
		this.customerArtists = customerArtists;
	}

	public CustomerArtist addCustomerArtist(CustomerArtist customerArtist) {
		getCustomerArtists().add(customerArtist);
		customerArtist.setArtist(this);

		return customerArtist;
	}

	public CustomerArtist removeCustomerArtist(CustomerArtist customerArtist) {
		getCustomerArtists().remove(customerArtist);
		customerArtist.setArtist(null);

		return customerArtist;
	}

}