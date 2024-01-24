package com.abm.mainet.authentication.security.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf()
                .ignoringAntMatchers("/PaymentController.html", "/CitizenHome.html", "/CitizenLogin.html",
                        "/ServiceApplicationStatus.html")
                .and()
                .authorizeRequests().anyRequest().permitAll()
                // .and().sessionManagement().maximumSessions(1).and().invalidSessionUrl("/CitizenHome.html")
                .and().headers().frameOptions().deny()
                .contentSecurityPolicy("frame-ancestors 'self'").and().xssProtection().block(true).and()
                .httpStrictTransportSecurity();

    }

}
