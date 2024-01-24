package com.abm.mainet.config;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.feature.LoggingFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@SuppressWarnings("deprecation")
@Configuration
@ComponentScan(basePackages = "com.abm.mainet")
@PropertySource(value = { "classpath:dms.properties" })
public class AppConfig {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean(name = Bus.DEFAULT_BUS_ID)
	public SpringBus springBus() {
		SpringBus bus = new SpringBus();
		bus.getFeatures().add(new LoggingFeature());
		return bus;
	}
}
