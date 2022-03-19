package com.appsdeveloperblog.productsservice.query;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appsdeveloperblog.core.events.ProductReservedEvent;
import com.appsdeveloperblog.productsservice.core.data.ProductEntity;
import com.appsdeveloperblog.productsservice.core.data.ProductsRepository;
import com.appsdeveloperblog.productsservice.core.events.ProductCreatedEvent;

@Component
@ProcessingGroup("product-group")
public class ProductEventHandler {
	
	private final ProductsRepository productsRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductEventHandler.class);
	
	@Autowired
	public ProductEventHandler(ProductsRepository productsRepository) {
		this.productsRepository = productsRepository;
	}

	@ExceptionHandler(resultType = IllegalArgumentException.class)
	public void handle(IllegalArgumentException exception) {
		throw exception;
	}
	
	@ExceptionHandler(resultType = Exception.class)
	public void handle(Exception exception) throws Exception {
		throw exception;
	}

	@EventHandler
	public void on(ProductCreatedEvent productCreatedEvent) throws Exception {
		
		ProductEntity productEntity = new ProductEntity();
		
		BeanUtils.copyProperties(productCreatedEvent, productEntity);
		
		try {
			productsRepository.save(productEntity);
		}
//		catch(IllegalArgumentException ex) {
//			ex.printStackTrace();
//		}
		catch(Exception ex) {
			
			ex.printStackTrace();
			throw ex;
		}
		
		//ListernerInvocationErrorHandler Testing code
		//if(true) throw new Exception("Forcefully throwing the exception from Event Handler!!!!");
			
		
	}
	
	@EventHandler
	public void on(ProductReservedEvent productReservedEvent) {
		ProductEntity productEntity = productsRepository.findByProductId(productReservedEvent.getProductId());
		productEntity.setQuantity(productEntity.getQuantity()-productReservedEvent.getQuantity());;
		productsRepository.save(productEntity);
		
		LOGGER.info("ProductReservedEvent occurred for orderId : "+ productReservedEvent.getOrderId()
		+" and product Id : "+productReservedEvent.getProductId());
	}

}
