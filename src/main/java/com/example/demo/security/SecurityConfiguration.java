package com.example.demo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/","index","/css/*","/js/*").permitAll()
                .antMatchers("/api/v1/users/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();

        // Disable CSRF if service used by non-browser clients.

        http.csrf().ignoringAntMatchers("/api/v1/users/**", "/h2-console/**");

        http.headers().frameOptions().sameOrigin();  // for h2-console to load (in dev profile)
    }

}