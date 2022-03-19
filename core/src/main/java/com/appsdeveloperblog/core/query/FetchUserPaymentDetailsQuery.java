package com.appsdeveloperblog.core.query;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FetchUserPaymentDetailsQuery {

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public FetchUserPaymentDetailsQuery(String userId) {
		this.userId = userId;
	}
	
	 
}
