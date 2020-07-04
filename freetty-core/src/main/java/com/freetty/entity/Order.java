package com.freetty.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the order database table.
 * 
 */
@Entity
@Table(name="order")
@NamedQuery(name="Order.findAll", query="SELECT o FROM Order o")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idf_order", insertable=false, updatable=false, unique=true, nullable=false)
	private int idfOrder;

	@Column(name="is_business_trip", length=1)
	private String isBusinessTrip;

	@Column(name="lead_time")
	private int leadTime;

	@Column(name="payment_method", length=1)
	private String paymentMethod;

	@Column(name="payment_state", length=1)
	private String paymentState;

	private int price;

	@Column(name="reg_date", insertable=false, updatable=false)
	private Timestamp regDate;

	@Column(name="reservation_end_time", length=5)
	private String reservationEndTime;

	@Column(name="reservation_start_time", length=5)
	private String reservationStartTime;

	@Column(name="service_place_addr1", length=100)
	private String servicePlaceAddr1;

	@Column(name="service_place_addr2", length=100)
	private String servicePlaceAddr2;

	//bi-directional many-to-one association to ArtistPortfolio
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idf_artist_portfolio")
	private ArtistPortfolio artistPortfolio;

	//bi-directional many-to-one association to Customer
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idf_customer")
	private Customer customer;

	public Order() {
	}

	public int getIdfOrder() {
		return this.idfOrder;
	}

	public void setIdfOrder(int idfOrder) {
		this.idfOrder = idfOrder;
	}

	public String getIsBusinessTrip() {
		return this.isBusinessTrip;
	}

	public void setIsBusinessTrip(String isBusinessTrip) {
		this.isBusinessTrip = isBusinessTrip;
	}

	public int getLeadTime() {
		return this.leadTime;
	}

	public void setLeadTime(int leadTime) {
		this.leadTime = leadTime;
	}

	public String getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentState() {
		return this.paymentState;
	}

	public void setPaymentState(String paymentState) {
		this.paymentState = paymentState;
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

	public String getReservationEndTime() {
		return this.reservationEndTime;
	}

	public void setReservationEndTime(String reservationEndTime) {
		this.reservationEndTime = reservationEndTime;
	}

	public String getReservationStartTime() {
		return this.reservationStartTime;
	}

	public void setReservationStartTime(String reservationStartTime) {
		this.reservationStartTime = reservationStartTime;
	}

	public String getServicePlaceAddr1() {
		return this.servicePlaceAddr1;
	}

	public void setServicePlaceAddr1(String servicePlaceAddr1) {
		this.servicePlaceAddr1 = servicePlaceAddr1;
	}

	public String getServicePlaceAddr2() {
		return this.servicePlaceAddr2;
	}

	public void setServicePlaceAddr2(String servicePlaceAddr2) {
		this.servicePlaceAddr2 = servicePlaceAddr2;
	}

	public ArtistPortfolio getArtistPortfolio() {
		return this.artistPortfolio;
	}

	public void setArtistPortfolio(ArtistPortfolio artistPortfolio) {
		this.artistPortfolio = artistPortfolio;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}