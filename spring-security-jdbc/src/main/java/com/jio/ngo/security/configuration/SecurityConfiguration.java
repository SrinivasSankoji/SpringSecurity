package com.jio.ngo.security.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception 
	{
		//Before Schema and Data Sql Files
		/**authenticationManagerBuilder.jdbcAuthentication()
		.dataSource(dataSource)
		.withDefaultSchema()
		.withUser(User.withUsername("user")
				.password("pass")
				.roles("USER"))
		.withUser(User.withUsername("admin")
				.password("pass")
				.roles("ADMIN")
		);**/
		
		////After Schema and Data Sql Files
		/**authenticationManagerBuilder.jdbcAuthentication()
		.dataSource(dataSource);*/
		
		//With Oracle or Any Other Database Connection
		//Mention the Details in application Properties File
		authenticationManagerBuilder.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery("")
		.authoritiesByUsernameQuery("");
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception 
	{
		httpSecurity.authorizeRequests()
		.antMatchers("/admin").hasRole("ADMIN")
		.antMatchers("user").hasAnyRole("USER","ADMIN")
		.antMatchers("/").permitAll()
		.and()
		.formLogin();
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder()
	{
		return NoOpPasswordEncoder.getInstance();
	}

}
