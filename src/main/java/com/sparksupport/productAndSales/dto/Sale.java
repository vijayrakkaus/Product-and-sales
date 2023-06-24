package com.sparksupport.productAndSales.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="sale")
public class Sale {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
	
	@Column(name = "quantity")
    private Integer quantity;
	
	@Column(name = "saleDate")
	@JsonFormat(pattern = "MM-dd-yyyy")
	@Temporal(TemporalType.TIMESTAMP)
    private Date saleDate;
    
    
	public Sale() {
		
	}

	public Sale(Long id, Product product, Integer quantity, Date saleDate) {
		super();
		this.id = id;
		this.setProduct(product);
		this.quantity = quantity;
		this.saleDate = saleDate;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Date getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}


}
