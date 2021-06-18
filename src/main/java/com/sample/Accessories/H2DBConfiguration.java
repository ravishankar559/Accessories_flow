package com.sample.Accessories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
 

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "h2dbEntityManagerFactory",
        transactionManagerRef = "h2dbTransactionManager",
        basePackages = {
                "com.sample.Accessories.H2Interfaces"
        })
public class H2DBConfiguration {
	
	@Bean("secondarydataSource")
	@ConfigurationProperties(prefix="spring.secondarydatasource")
	public DataSource dataSource() {
	    return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "h2dbEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean h2dbEntityManagerFactory(
            final EntityManagerFactoryBuilder builder,
            @Qualifier("secondarydataSource") final DataSource dataSource) {
        // dynamically setting up the hibernate properties for each of the datasource.
        final Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        // in springboot2 the dialect can be automatically detected.
        // we are setting up here just to avoid any incident.
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        return builder
                .dataSource(dataSource)
                .properties(properties)
                .packages("com.sample.Accessories.H2Entity")
                .build();
    }
 
    @Bean(name = "h2dbTransactionManager")
    public PlatformTransactionManager h2dbTransactionManager(
            @Qualifier("h2dbEntityManagerFactory") final EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
