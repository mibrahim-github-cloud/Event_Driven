package com.appsdeveloperblog.orderservice.saga;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.appsdeveloperblog.core.commands.ReserveProductCommand;
import com.appsdeveloperblog.core.events.ProductReservedEvent;
import com.appsdeveloperblog.core.model.User;
import com.appsdeveloperblog.core.query.FetchUserPaymentDetailsQuery;
import com.appsdeveloperblog.orderservice.core.events.OrderCreatedEvent;

@Saga
public class OrderSaga {

	@Autowired
	private transient CommandGateway commandGateway;

	@Autowired
	private transient QueryGateway queryGateway;

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderSaga.class);

	@StartSaga
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(OrderCreatedEvent orderCreatedEvent) {

		ReserveProductCommand reserveProductCommand = new ReserveProductCommand(orderCreatedEvent.getProductId(),
				orderCreatedEvent.getQuantity(), orderCreatedEvent.getOrderId(), orderCreatedEvent.getUserId());

		LOGGER.info("OrderCreatedEvent Handled for orderId : " + reserveProductCommand.getOrderId()
				+ " and product Id : " + reserveProductCommand.getProductId());
		try {
			commandGateway.send(reserveProductCommand, new CommandCallback<ReserveProductCommand, Object>() {

				@Override
				public void onResult(CommandMessage<? extends ReserveProductCommand> commandMessage,
						CommandResultMessage<? extends Object> commandResultMessage) {
					if (commandResultMessage.isExceptional()) {
						// compensation transaction
					}

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SagaEventHandler(associationProperty = "orderId")
	public void handle(ProductReservedEvent productReservedEvent) {
		// process User Payment
		LOGGER.info("ProductReservedEvent is called for orderId : " + productReservedEvent.getOrderId()
				+ " and product Id : " + productReservedEvent.getProductId());

		FetchUserPaymentDetailsQuery fetchUserPaymentDetailsQuery = new FetchUserPaymentDetailsQuery(
				productReservedEvent.getUserId());

		User userPaymentDetails = null;

		try {
			userPaymentDetails = queryGateway.query(fetchUserPaymentDetailsQuery, ResponseTypes.instanceOf(User.class)).join();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());

			// Compensating Transaction
			return;
		}

		if(userPaymentDetails == null) {
			// Compensating Transaction
			return;
		}
		LOGGER.info("Successfully fetched user payment details for user " + userPaymentDetails.getFirstName());

	}

}
