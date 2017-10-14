package com.cqprecheck.precheck.Security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityExclusionConfiguration extends WebSecurityConfigurerAdapter {
    String [] publicUrls = new String [] {
            "/api/add-account/new"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/add-account/new").permitAll()
                .antMatchers("/**").authenticated()
                .and().csrf().disable();
    }
}
