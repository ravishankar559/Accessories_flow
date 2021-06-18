package com.sample.Accessories.webServiceClients;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class LeaseEligibilityConfiguration {
	
	  @Bean
	  public Jaxb2Marshaller marshaller() {
	    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
	    marshaller.setContextPath("com.sample.Accessories.webservices.LeaseEligibility");
	    return marshaller;
	  }

	  @Bean
	  public LeaseEligibilityClient leaseEligibilityClient(Jaxb2Marshaller marshaller) {
		LeaseEligibilityClient client = new LeaseEligibilityClient();
	    client.setDefaultUri("http://localhost:8030/ws");
	    client.setMarshaller(marshaller);
	    client.setUnmarshaller(marshaller);
	    return client;
	  }

}





