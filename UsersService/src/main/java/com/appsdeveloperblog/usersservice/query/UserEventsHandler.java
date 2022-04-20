package com.appsdeveloperblog.usersservice.query;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import com.appsdeveloperblog.core.model.PaymentDetails;
import com.appsdeveloperblog.core.model.User;
import com.appsdeveloperblog.core.query.FetchUserPaymentDetailsQuery;

@Component
public class UserEventsHandler {

	@QueryHandler
    public User findUserPaymentDetails(FetchUserPaymentDetailsQuery query) {
        
        PaymentDetails paymentDetails = new PaymentDetails(
        		"MOHAMMED IBRAHIM",
        		"123Card",
        		12,
        		2030,
        		"123"
        		);

        User user = new User(
        		"Mohammed",
        		"Ibrahim",
                query.getUserId(),
                paymentDetails
                );

        return user;
    }
}
