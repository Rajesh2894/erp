package com.abm.mainet.security.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        //Defect #31605: Handled case wherein OTP verification page was not loading
        //Change: added "/AdminOTPVerification.html" in ignoringAntMatchers list

         http.csrf()
        .ignoringAntMatchers("/PaymentController.html", "/AdminLogin.html","/AdminOTPVerification.html","/Home.html","/AdminUpdatePersonalDtls.html")
        .and()
        .authorizeRequests().anyRequest().permitAll().and()
        .sessionManagement().maximumSessions(1).and().invalidSessionUrl("/Home.html")
        .and().headers().frameOptions().deny()
        .contentSecurityPolicy("frame-ancestors 'self'").and().xssProtection().block(true).and().httpStrictTransportSecurity();
    }

}
