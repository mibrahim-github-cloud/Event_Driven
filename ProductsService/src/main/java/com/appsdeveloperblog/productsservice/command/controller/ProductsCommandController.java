package com.appsdeveloperblog.productsservice.command.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.productsservice.command.CreateProductCommand;
import com.appsdeveloperblog.productsservice.model.CreateProductRequestModel;

@RestController
@RequestMapping("/products")
public class ProductsCommandController {

	private Environment env;
	private CommandGateway commandGateway;

	@Autowired
	public ProductsCommandController(Environment env, CommandGateway commandGateway) {
		this.env = env;
		this.commandGateway = commandGateway;

	}

	@PostMapping
	public String createProduct(@Valid @RequestBody CreateProductRequestModel createProductRequestModel) {

		CreateProductCommand createProductCommand = new CreateProductCommand(UUID.randomUUID().toString(),
				createProductRequestModel.getPrice(), createProductRequestModel.getQuantity(),
				createProductRequestModel.getName());

		String commandGatewayReturnValue;
		commandGatewayReturnValue = commandGateway.sendAndWait(createProductCommand);
		
//		try {
//			commandGatewayReturnValue = commandGateway.sendAndWait(createProductCommand);
//		} catch (Exception e) {
//			commandGatewayReturnValue = e.getLocalizedMessage();
//		}

		return commandGatewayReturnValue;
	}

	
//	 @GetMapping public String getProduct() { return
//	 "HTTP GET Method "+env.getProperty("local.server.port"); }
//	 
//	 @PutMapping public String updateProduct() { return "HTTP update Method"; }
//	  
//	 @DeleteMapping public String deleteProduct() { return "HTTP Delete Method"; }
//	 
}
