package com.sum.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.sum.config.DatabaseConfig;

@Configuration
@EnableWebSecurity
@Import(value = DatabaseConfig.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/resources/**").permitAll() 
			.anyRequest().authenticated().and()
		.formLogin()
			.loginPage("/login").failureUrl("/login?error").
			loginProcessingUrl("/loginFormAction").
			usernameParameter("username").
			passwordParameter("password").
			defaultSuccessUrl("/calendar", true).
			permitAll().
		and()
			.logout().permitAll();
	}

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("depe").password("depe").roles("ADMIN");
//		auth.inMemoryAuthentication().withUser("cami").password("cami").roles("USER");
//	}

}