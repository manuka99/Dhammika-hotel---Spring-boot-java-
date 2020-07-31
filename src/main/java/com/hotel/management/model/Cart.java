package com.hotel.management.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "cart")
public class Cart {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "cart_id")
	private String cartID;
	private int itemCount;
	private double productPriceTotal;
	private double tax;
	private double shippingFee;
	private double total;

	@OneToOne()
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@OrderBy("productid")
	private Set<Cart_Items> cart_Items;

	public Cart() {
		super();
		this.itemCount = 0;
		this.total = 0;
		this.shippingFee = 0;
		this.tax = 0;
		this.productPriceTotal = 0;
	}

	public String getCartID() {
		return cartID;
	}

	public void setCartID(String cartID) {
		this.cartID = cartID;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public double getProductPriceTotal() {
		return productPriceTotal;
	}

	public void setProductPriceTotal(double productPriceTotal) {
		this.productPriceTotal = productPriceTotal;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(double shippingFee) {
		this.shippingFee = shippingFee;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Cart_Items> getCart_Items() {
		return cart_Items;
	}

	public void setCart_Items(Set<Cart_Items> cart_Items) {
		this.cart_Items = cart_Items;
	}

}
