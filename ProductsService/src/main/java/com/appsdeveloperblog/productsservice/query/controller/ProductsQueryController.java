package com.appsdeveloperblog.productsservice.query.controller;

import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryResponseMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.productsservice.model.FindProductResponseModel;
import com.appsdeveloperblog.productsservice.query.FindProductQuery;

@RestController
@RequestMapping("/products")
public class ProductsQueryController {
	
	private final QueryGateway queryGateway;
	
	public ProductsQueryController(QueryGateway queryGateway) {
		this.queryGateway = queryGateway;
	}

	@GetMapping
	public List<FindProductResponseModel> findProducts(){
		FindProductQuery findProductQuery = new FindProductQuery();
		
		List<FindProductResponseModel> products = queryGateway.query(findProductQuery, ResponseTypes.multipleInstancesOf(FindProductResponseModel.class)).join();
		
		return products;
	}
	
	

}
