package com.accountmgr.ui.config;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
 
@Configuration
@EnableAutoConfiguration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
	@Autowired
	DataSource dataSource;
 
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		// retrieve username and password from the 'users' table, in the PostgreSQL database.
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select username, password, enabled from users where username=?")
				.authoritiesByUsernameQuery("select username, role from user_roles where username=?");
	}
 	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// After login automatically redirect to the 'account' page
		// Added also a 'USER' role check - currently this is not used, 
		// but the infrastructure is there to create users with different roles (the user_roles table exists)
		http.authorizeRequests().antMatchers("/**")
		.hasRole("USER").and().formLogin()
		.defaultSuccessUrl("/account", true);
	}
	
}