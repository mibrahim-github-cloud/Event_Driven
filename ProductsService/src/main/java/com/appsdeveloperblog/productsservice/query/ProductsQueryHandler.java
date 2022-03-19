package com.appsdeveloperblog.productsservice.query;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.appsdeveloperblog.productsservice.core.data.ProductEntity;
import com.appsdeveloperblog.productsservice.core.data.ProductsRepository;
import com.appsdeveloperblog.productsservice.model.FindProductResponseModel;

@Component
public class ProductsQueryHandler {
	
	private final ProductsRepository productsRepository;
	
	public ProductsQueryHandler(ProductsRepository productsRepository) {
		this.productsRepository = productsRepository;
	}

	@QueryHandler
	public List<FindProductResponseModel> findProducts(FindProductQuery findProductQuery){
		
		List<FindProductResponseModel> productsResponse = new ArrayList<>();
		
		List<ProductEntity> jpaProductsResponse = productsRepository.findAll();
		
		productsResponse = jpaProductsResponse.stream().map(product->{
			FindProductResponseModel findProductResponseModel = new FindProductResponseModel();
			BeanUtils.copyProperties(product, findProductResponseModel);
			return findProductResponseModel;
		}).collect(Collectors.toList());
		
		return productsResponse;
		
	}

}
