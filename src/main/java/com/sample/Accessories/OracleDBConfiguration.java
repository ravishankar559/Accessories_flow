package com.sample.Accessories;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "dbEntityManagerFactory",
        transactionManagerRef = "dbTransactionManager",
        basePackages = {
                "com.sample.Accessories.Interfaces"
        })
public class OracleDBConfiguration {
	
	@Bean("datasource")
	@ConfigurationProperties(prefix="spring.datasource")
	@Primary
	public DataSource dataSource() {
	    return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "dbEntityManagerFactory")
	@Primary
    public LocalContainerEntityManagerFactoryBean dbEntityManagerFactory(
            final EntityManagerFactoryBuilder builder,
            @Qualifier("datasource") final DataSource dataSource) {
        // dynamically setting up the hibernate properties for each of the datasource.
        final Map<String, String> properties = new HashMap<>();
        // in springboot2 the dialect can be automatically detected.
        // we are setting up here just to avoid any incident.
        properties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        return builder
                .dataSource(dataSource)
                .properties(properties)
                .packages("com.sample.Accessories.Entity")
                .build();
    }
 
    @Bean(name = "dbTransactionManager")
    @Primary
    public PlatformTransactionManager dbTransactionManager(
            @Qualifier("dbEntityManagerFactory") final EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
