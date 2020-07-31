package com.hotel.management.model;

import java.util.Base64;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String productID;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "price")
	private double price;

	@Column(name = "tax")
	private double tax;

	@Column(name = "portion")
	private String portion;

	@Column(name = "active")
	private boolean active;

	@Lob
	@Column(name = "imageData")
	private byte[] imageData;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	@Transient
	private String image64String;

	@OneToMany(mappedBy = "product")
	private Set<Cart_Items> cart_Items;

	@OneToMany(mappedBy = "product")
	private Set<Order_Items> order_Items;

	@OneToMany(mappedBy = "product", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<FeedBack> feedbacks;

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public String getPortion() {
		return portion;
	}

	public void setPortion(String portion) {
		this.portion = portion;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public String getImage64String() {

		String imageBase64String = "";

		try {
			imageBase64String = Base64.getEncoder().encodeToString(this.getImageData());
		} catch (NullPointerException e) {
			// TODO: handle exception
		}

		return imageBase64String;
	}

	public void setImage64String(String image64String) {
		this.image64String = image64String;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Set<Cart_Items> getCart_Items() {
		return cart_Items;
	}

	public void setCart_Items(Set<Cart_Items> cart_Items) {
		this.cart_Items = cart_Items;
	}

	public Set<Order_Items> getOrder_Items() {
		return order_Items;
	}

	public void setOrder_Items(Set<Order_Items> order_Items) {
		this.order_Items = order_Items;
	}

	public Set<FeedBack> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(Set<FeedBack> feedbacks) {
		this.feedbacks = feedbacks;
	}

}
