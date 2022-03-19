package com.appsdeveloperblog.productsservice.command;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.appsdeveloperblog.core.commands.ReserveProductCommand;
import com.appsdeveloperblog.core.events.ProductReservedEvent;
import com.appsdeveloperblog.productsservice.core.events.ProductCreatedEvent;

@Aggregate
public class ProductAggregate {

	private final static Logger LOGGER = LoggerFactory.getLogger(ProductAggregate.class);
	
	@AggregateIdentifier
	private String productId;
	private BigDecimal price;
	private Integer quantity;
	private String name;
	
	public ProductAggregate() {
		
	}
	
	@CommandHandler
	public ProductAggregate(CreateProductCommand createProductCommand) throws Exception {
		//Validate create product command
		
		if(createProductCommand.getPrice().compareTo(BigDecimal.ZERO)<=0) {
			throw new IllegalArgumentException("Price cannot be else than or equal to zero");
		}
		
		if(createProductCommand.getName() == null || createProductCommand.getName().isBlank()) {
			throw new IllegalArgumentException("Title cannot be empty");
		}
		
		ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
		
		BeanUtils.copyProperties(createProductCommand, productCreatedEvent);
		
		AggregateLifecycle.apply(productCreatedEvent);
		
		//CommandExcutionException Testing code
		//if(true) throw new Exception("Forcefully throwing the exception from command handler!!!!");
	}
	
	@CommandHandler
	public void handle(ReserveProductCommand reserveProductCommand) {
		LOGGER.info("ReserveProductCommand Handled for orderId : "+ reserveProductCommand.getOrderId()
		+" and product Id : "+reserveProductCommand.getProductId());
		
		if(quantity< reserveProductCommand.getQuantity()) {
			throw new IllegalArgumentException("Insufficient Number of intems in stock...");
		}
		
		ProductReservedEvent productReservedEvent = new ProductReservedEvent(
				reserveProductCommand.getProductId(), 
				reserveProductCommand.getQuantity(), 
				reserveProductCommand.getOrderId(), 
				reserveProductCommand.getUserId()
				);
		
		AggregateLifecycle.apply(productReservedEvent);
		
		LOGGER.info("ReserveProductCommand Handled for orderId : "+ reserveProductCommand.getOrderId()
		+" and product Id : "+reserveProductCommand.getProductId());
	}
	
	@EventSourcingHandler
	public void on(ProductCreatedEvent productCreatedEvent) {
		this.name = productCreatedEvent.getName();
		this.price = productCreatedEvent.getPrice();
		this.quantity = productCreatedEvent.getQuantity();
		this.productId = productCreatedEvent.getProductId();
	}
	
	@EventSourcingHandler
	public void on(ProductReservedEvent productReservedEvent) {
		this.quantity -=productReservedEvent.getQuantity();
		
		LOGGER.info("ProductReservedEvent Handled for orderId : "+ productReservedEvent.getOrderId()
		+" and product Id : "+productReservedEvent.getProductId());
	}
	
}
