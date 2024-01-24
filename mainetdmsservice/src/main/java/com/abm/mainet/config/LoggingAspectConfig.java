package com.abm.mainet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

import com.abm.mainet.config.logging.LoggingAspect;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfig {

    @Bean
    public LoggingAspect loggingAspect(Environment env) {
        return new LoggingAspect();
    }

}
