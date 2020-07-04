package com.freetty.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the customer database table.
 * 
 */
@Entity
@Table(name="customer")
@NamedQuery(name="Customer.findAll", query="SELECT c FROM Customer c")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idf_customer", unique=true, nullable=false)
	private int idfCustomer;

	@Column(length=100)
	private String addr1;

	@Column(length=100)
	private String addr2;

	@Column(length=8)
	private String birthday;

	@Column(length=45)
	private String email;

	@Column(length=1)
	private String gender;

	@Column(length=11)
	private String mobile;

	@Column(length=45)
	private String name;

	@Column(length=45)
	private String nickname;

	@Column(length=64)
	private String password;

	@Column(length=2)
	private String platform;

	@Column(name="reg_date", insertable=false, updatable=false, nullable=false)
	private Timestamp regDate;

	@Column(name="special_description", length=100)
	private String specialDescription;

	//bi-directional many-to-one association to ArtistCustomer
	@OneToMany(mappedBy="customer")
	private List<ArtistCustomer> artistCustomers;

	//bi-directional many-to-one association to CustomerArtist
	@OneToMany(mappedBy="customer")
	private List<CustomerArtist> customerArtists;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="customer")
	private List<Order> orders;

	//bi-directional many-to-one association to WishlistPortfolio
	@OneToMany(mappedBy="customer")
	private List<WishlistPortfolio> wishlistPortfolios;

	public Customer() {
	}

	public int getIdfCustomer() {
		return this.idfCustomer;
	}

	public void setIdfCustomer(int idfCustomer) {
		this.idfCustomer = idfCustomer;
	}

	public String getAddr1() {
		return this.addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return this.addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
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

	public String getPlatform() {
		return this.platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Timestamp getRegDate() {
		return this.regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	public String getSpecialDescription() {
		return this.specialDescription;
	}

	public void setSpecialDescription(String specialDescription) {
		this.specialDescription = specialDescription;
	}

	public List<ArtistCustomer> getArtistCustomers() {
		return this.artistCustomers;
	}

	public void setArtistCustomers(List<ArtistCustomer> artistCustomers) {
		this.artistCustomers = artistCustomers;
	}

	public ArtistCustomer addArtistCustomer(ArtistCustomer artistCustomer) {
		getArtistCustomers().add(artistCustomer);
		artistCustomer.setCustomer(this);

		return artistCustomer;
	}

	public ArtistCustomer removeArtistCustomer(ArtistCustomer artistCustomer) {
		getArtistCustomers().remove(artistCustomer);
		artistCustomer.setCustomer(null);

		return artistCustomer;
	}

	public List<CustomerArtist> getCustomerArtists() {
		return this.customerArtists;
	}

	public void setCustomerArtists(List<CustomerArtist> customerArtists) {
		this.customerArtists = customerArtists;
	}

	public CustomerArtist addCustomerArtist(CustomerArtist customerArtist) {
		getCustomerArtists().add(customerArtist);
		customerArtist.setCustomer(this);

		return customerArtist;
	}

	public CustomerArtist removeCustomerArtist(CustomerArtist customerArtist) {
		getCustomerArtists().remove(customerArtist);
		customerArtist.setCustomer(null);

		return customerArtist;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setCustomer(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setCustomer(null);

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
		wishlistPortfolio.setCustomer(this);

		return wishlistPortfolio;
	}

	public WishlistPortfolio removeWishlistPortfolio(WishlistPortfolio wishlistPortfolio) {
		getWishlistPortfolios().remove(wishlistPortfolio);
		wishlistPortfolio.setCustomer(null);

		return wishlistPortfolio;
	}

}