package com.user.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product_transaction")
public class ProductTransaction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private String id;

	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "product_id")
	private Product product;

	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "buyer_user_id")
	private User buyerUserId;

	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "seller_user_id")
	private User sellerUserId;

	@Column(name = "created_date")
	private String createdDate;

	public String getId() {
		return id;
	}

	public Product getProduct() {
		return product;
	}

	public User getBuyerUserId() {
		return buyerUserId;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setBuyerUserId(User buyerUserId) {
		this.buyerUserId = buyerUserId;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public User getSellerUserId() {
		return sellerUserId;
	}

	public void setSellerUserId(User sellerUserId) {
		this.sellerUserId = sellerUserId;
	}

}
