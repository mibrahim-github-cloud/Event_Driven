package com.appsdeveloperblog.productsservice.command;


import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import com.appsdeveloperblog.productsservice.core.data.ProductLookupEntity;
import com.appsdeveloperblog.productsservice.core.data.ProductLookupRepository;
import com.appsdeveloperblog.productsservice.core.events.ProductCreatedEvent;

@Component
@ProcessingGroup("product-group")
public class ProductLookupEventHandler {
	
	private final ProductLookupRepository productLookupRepository;
	
	
	public ProductLookupEventHandler(ProductLookupRepository productLookupRepository) {
		this.productLookupRepository = productLookupRepository;
	}


	@EventHandler
	public void on(ProductCreatedEvent productCreatedEvent) {
		
		ProductLookupEntity productLookupEntity = new ProductLookupEntity(productCreatedEvent.getProductId(),productCreatedEvent.getName());
		
		productLookupRepository.save(productLookupEntity);
	}

}
