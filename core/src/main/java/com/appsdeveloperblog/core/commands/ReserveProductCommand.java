package com.appsdeveloperblog.core.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ReserveProductCommand {

	@TargetAggregateIdentifier
	private final String productId;
	private final int quantity;
	private final String orderId;
	private final String userId;
	
	public String getProductId() {
		return productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public String getOrderId() {
		return orderId;
	}
	public String getUserId() {
		return userId;
	}
	
	
	
	public ReserveProductCommand(String productId, int quantity, String orderId, String userId) {
		this.productId = productId;
		this.quantity = quantity;
		this.orderId = orderId;
		this.userId = userId;
	}
	
	
	
	
	
}
