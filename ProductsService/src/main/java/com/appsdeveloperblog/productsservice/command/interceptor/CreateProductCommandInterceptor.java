package com.appsdeveloperblog.productsservice.command.interceptor;

import java.util.List;
import java.util.function.BiFunction;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.appsdeveloperblog.productsservice.command.CreateProductCommand;
import com.appsdeveloperblog.productsservice.core.data.ProductLookupEntity;
import com.appsdeveloperblog.productsservice.core.data.ProductLookupRepository;


@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>>{

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateProductCommandInterceptor.class);
	
	private final ProductLookupRepository productLookupRepository;
	
	public CreateProductCommandInterceptor(ProductLookupRepository productLookupRepository) {
		super();
		this.productLookupRepository = productLookupRepository;
	}



	@Override
	public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
			List<? extends CommandMessage<?>> messages) {
		
		return (index, command)->{
			
			LOGGER.info("Intercepted command : "+command.getPayloadType());
			
			if(CreateProductCommand.class.equals(command.getPayloadType())) {
				
				CreateProductCommand createProductCommand = (CreateProductCommand) command.getPayload();
				
//				if(createProductCommand.getPrice().compareTo(BigDecimal.ZERO)<=0) {
//					throw new IllegalArgumentException("Price cannot be else than or equal to zero");
//				}
//				
//				if(createProductCommand.getName() == null || createProductCommand.getName().isBlank()) {
//					throw new IllegalArgumentException("Title cannot be empty");
//				}
				
				ProductLookupEntity productLookupEntity = productLookupRepository.findByProductIdOrName(createProductCommand.getProductId(), createProductCommand.getName());
				
				if(productLookupEntity!=null) {
					throw new IllegalStateException(
							String.format("Product with productId %s with name %s is already available", 
									createProductCommand.getProductId(), createProductCommand.getName())
							);
				}
			}
			return command;
		};
	}

}
