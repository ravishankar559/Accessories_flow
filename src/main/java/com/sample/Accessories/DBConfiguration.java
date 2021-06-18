package com.sample.Accessories;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

	@Configuration
	@ConfigurationProperties("spring.datasource")
	@SuppressWarnings("unused")
	public class DBConfiguration
	{
	
	@Value("${spring.datasource.driverClassName}")
	private String driverClassName;


	@Value("${spring.datasource.jdbc-url}")
	private String url;

		
		@Profile("rtb2")
		@Bean
		public String dummy() {

			System.out.println("DB Connected to RTB2 backend");
	              System.out.println(driverClassName);
	 		System.out.println(url);
			return "DB Connected to RTB2 backend";
		     }

		

		@Profile("st2")
		@Bean
		public String dummyst2() {

			System.out.println("DB Connected to ST2 backend");
	              System.out.println(driverClassName);
	 		System.out.println(url);
			return "DB Connected to ST2 backend";
		     }


}
