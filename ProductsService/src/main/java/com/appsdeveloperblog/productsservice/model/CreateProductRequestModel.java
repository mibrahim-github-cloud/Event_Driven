package com.appsdeveloperblog.productsservice.model;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CreateProductRequestModel {

	@Min(value=1, message = "Price canot be lower than 1")
	private BigDecimal price;
	
	@Min(value=1, message = "Quantity canot be lower than 1")
	@Max(value=5, message = "Quantity canot be higher than 5")
	private Integer quantity;
	@NotBlank(message = "Product title is a required field")
	private String name;
	
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
