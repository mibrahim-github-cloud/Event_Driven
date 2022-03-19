package com.appsdeveloperblog.productsservice.core.events;

import java.math.BigDecimal;

import org.axonframework.modelling.command.AggregateIdentifier;

import lombok.Data;

@Data
public class ProductCreatedEvent {

	@AggregateIdentifier
	private String productId;
	private BigDecimal price;
	private Integer quantity;
	private String name;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
