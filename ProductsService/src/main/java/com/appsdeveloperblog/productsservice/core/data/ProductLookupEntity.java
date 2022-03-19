package com.appsdeveloperblog.productsservice.core.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="productlookup")
public class ProductLookupEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true)
	private String productId;
	@Column(unique = true)
	private String name;
	
	
	public ProductLookupEntity() {
	
	}

	public ProductLookupEntity(String productId, String name) {
		this.productId = productId;
		this.name = name;
	}

	
}
