package com.appsdeveloperblog.productsservice.command;

import java.math.BigDecimal;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateProductCommand {

	@TargetAggregateIdentifier
    private final String productId;
	private final BigDecimal price;
	private final Integer quantity;
	private final String name;
	
	public CreateProductCommand(String productId, BigDecimal price, Integer quantity, String name) {
		this.productId = productId;
		this.price = price;
		this.quantity = quantity;
		this.name = name;
	}

	public String getProductId() {
		return productId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public String getName() {
		return name;
	}

	
	
	
	
}
