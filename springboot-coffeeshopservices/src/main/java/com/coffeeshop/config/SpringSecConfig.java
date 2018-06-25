package com.coffeeshop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SpringSecConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {        
    	httpSecurity.authorizeRequests().antMatchers("/","/swagger-resources").permitAll();
    	httpSecurity.authorizeRequests().anyRequest().fullyAuthenticated();
    	httpSecurity.httpBasic();
    	httpSecurity.csrf().disable();
    }


}