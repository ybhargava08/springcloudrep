package com.yb.retreiveandsave.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class CustomSecurity extends WebSecurityConfigurerAdapter {
	
	
	protected void configure(HttpSecurity security) throws Exception{
        security.csrf().disable().authorizeRequests().antMatchers("/","/info","/status","/save","/getLatestData","/getTagCloudData","/fetchDataBasedOnInput/*")
        .permitAll().anyRequest().authenticated().and()
        .httpBasic().and().authorizeRequests().antMatchers("/deleteAll").hasAuthority("ADMIN").anyRequest().authenticated();
	}
	
	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder manager) throws Exception{
		manager.inMemoryAuthentication().withUser("ybhargava08").password("E@rth2013").authorities("ADMIN");
	}
}
