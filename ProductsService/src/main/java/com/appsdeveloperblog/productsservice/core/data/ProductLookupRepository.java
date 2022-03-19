package com.appsdeveloperblog.productsservice.core.data;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductLookupRepository extends JpaRepository<ProductLookupEntity, String>{

	ProductLookupEntity findByProductIdOrName(String productId, String name);
}
