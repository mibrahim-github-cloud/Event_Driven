package com.appsdeveloperblog.productsservice;


import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import com.appsdeveloperblog.core.configuration.AxonConfig;
import com.appsdeveloperblog.productsservice.command.interceptor.CreateProductCommandInterceptor;
import com.appsdeveloperblog.productsservice.errorhandling.ProductServiceEventErrorHandler;

@EnableDiscoveryClient
@SpringBootApplication
@Import({ AxonConfig.class })
public class ProductsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsServiceApplication.class, args);
	}

	@Autowired
	public void registerCreateProductCommandInerceptor(ApplicationContext context, 
			CommandBus commandBus) {
		
		commandBus.registerDispatchInterceptor(context.getBean(CreateProductCommandInterceptor.class));
		
	}
	
	@Autowired
	public void configure(EventProcessingConfigurer config) {
		config.registerListenerInvocationErrorHandler("product-group",
				conf->new ProductServiceEventErrorHandler());
		
//		config.registerListenerInvocationErrorHandler("product-group",
//				conf-> PropagatingErrorHandler.instance());
	}
}
