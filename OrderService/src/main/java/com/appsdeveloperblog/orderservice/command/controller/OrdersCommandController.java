/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appsdeveloperblog.orderservice.command.controller;


import java.util.UUID;

import javax.validation.Valid;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.orderservice.command.CreateOrderCommand;
import com.appsdeveloperblog.orderservice.core.model.OrderStatus;
import com.appsdeveloperblog.orderservice.model.CreateOrderRequestModel;

@RestController
@RequestMapping("/orders")
public class OrdersCommandController {

    private final CommandGateway commandGateway;
    
    
	
    public OrdersCommandController(CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
	}

	@PostMapping
    public String createOrder(@Valid @RequestBody CreateOrderRequestModel order) {
        
        String userId = "27b95829-4f3f-4ddf-8983-151ba010e35b";

        CreateOrderCommand createOrderCommand = new CreateOrderCommand(
        		UUID.randomUUID().toString(),
        		userId, 
       		    order.getProductId(), 
        		order.getQuantity(), 
        		order.getAddressId(), 
        		OrderStatus.CREATED
        		);
      
        return commandGateway.sendAndWait(createOrderCommand);

    }

}
