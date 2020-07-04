package com.freetty.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the artist_portfolio database table.
 * 
 */
@Entity
@Table(name="artist_portfolio")
@NamedQuery(name="ArtistPortfolio.findAll", query="SELECT a FROM ArtistPortfolio a")
public class ArtistPortfolio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idf_artist_portfolio", unique=true, nullable=false)
	private int idfArtistPortfolio;

	@Column(name="cnt_view")
	private int cntView;

	@Column(length=300)
	private String description;

	@Column(length=1)
	private String gender;

	@Column(name="is_business_trip", length=1)
	private String isBusinessTrip;

	@Column(name="is_deploy", length=1)
	private String isDeploy;

	@Column(name="ready_time")
	private Integer readyTime;

	@Column(name="reg_date", insertable=false, updatable=false, nullable=false)
	private Timestamp regDate;

	@Column(length=60)
	private String subject;

	@Column(length=150)
	private String tag;

	//bi-directional many-to-one association to Artist
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idf_artist", nullable=false)
	private Artist artist;

	//bi-directional many-to-one association to ArtistKind
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idf_artist_kind")
	private ArtistKind artistKind;

	//bi-directional many-to-one association to ArtistPortfolioService
	@OneToMany(mappedBy="artistPortfolio")
	private List<ArtistPortfolioService> artistPortfolioServices;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="artistPortfolio")
	private List<Order> orders;

	//bi-directional many-to-one association to WishlistPortfolio
	@OneToMany(mappedBy="artistPortfolio")
	private List<WishlistPortfolio> wishlistPortfolios;

	public ArtistPortfolio() {
	}

	public int getIdfArtistPortfolio() {
		return this.idfArtistPortfolio;
	}

	public void setIdfArtistPortfolio(int idfArtistPortfolio) {
		this.idfArtistPortfolio = idfArtistPortfolio;
	}

	public int getCntView() {
		return this.cntView;
	}

	public void setCntView(int cntView) {
		this.cntView = cntView;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIsBusinessTrip() {
		return this.isBusinessTrip;
	}

	public void setIsBusinessTrip(String isBusinessTrip) {
		this.isBusinessTrip = isBusinessTrip;
	}

	public String getIsDeploy() {
		return this.isDeploy;
	}

	public void setIsDeploy(String isDeploy) {
		this.isDeploy = isDeploy;
	}

	public Integer getReadyTime() {
		return this.readyTime;
	}

	public void setReadyTime(Integer readyTime) {
		this.readyTime = readyTime;
	}

	public Timestamp getRegDate() {
		return this.regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Artist getArtist() {
		return this.artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public ArtistKind getArtistKind() {
		return this.artistKind;
	}

	public void setArtistKind(ArtistKind artistKind) {
		this.artistKind = artistKind;
	}

	public List<ArtistPortfolioService> getArtistPortfolioServices() {
		return this.artistPortfolioServices;
	}

	public void setArtistPortfolioServices(List<ArtistPortfolioService> artistPortfolioServices) {
		this.artistPortfolioServices = artistPortfolioServices;
	}

	public ArtistPortfolioService addArtistPortfolioService(ArtistPortfolioService artistPortfolioService) {
		getArtistPortfolioServices().add(artistPortfolioService);
		artistPortfolioService.setArtistPortfolio(this);

		return artistPortfolioService;
	}

	public ArtistPortfolioService removeArtistPortfolioService(ArtistPortfolioService artistPortfolioService) {
		getArtistPortfolioServices().remove(artistPortfolioService);
		artistPortfolioService.setArtistPortfolio(null);

		return artistPortfolioService;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setArtistPortfolio(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setArtistPortfolio(null);

		return order;
	}

	public List<WishlistPortfolio> getWishlistPortfolios() {
		return this.wishlistPortfolios;
	}

	public void setWishlistPortfolios(List<WishlistPortfolio> wishlistPortfolios) {
		this.wishlistPortfolios = wishlistPortfolios;
	}

	public WishlistPortfolio addWishlistPortfolio(WishlistPortfolio wishlistPortfolio) {
		getWishlistPortfolios().add(wishlistPortfolio);
		wishlistPortfolio.setArtistPortfolio(this);

		return wishlistPortfolio;
	}

	public WishlistPortfolio removeWishlistPortfolio(WishlistPortfolio wishlistPortfolio) {
		getWishlistPortfolios().remove(wishlistPortfolio);
		wishlistPortfolio.setArtistPortfolio(null);

		return wishlistPortfolio;
	}

}