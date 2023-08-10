package com.example.indatacore_backend.security;

import com.example.indatacore_backend.services.UserService;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	private String[] AUTHORIZED_ROUTES = {
			"/login/**",
			"/register/**",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/v2/api-docs",
			"/webjars/**"
	};
	
	
	final UserService userDetailsService; 
	final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	public WebSecurity(UserService userDetailsService,BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userDetailsService=userDetailsService;
		this.bCryptPasswordEncoder=bCryptPasswordEncoder;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors();
		http.csrf().disable();
		http.authorizeRequests().antMatchers(AUTHORIZED_ROUTES).permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST,SecurityConstants.SIGN_UP_URL).permitAll();
		http.authorizeRequests().anyRequest().authenticated();
 		http.addFilter(getAuthenticationFilter());
		http.addFilter(new AuthorizationFilter(authenticationManager()));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	protected AuthenticationFilter getAuthenticationFilter() throws Exception {
		final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
		filter.setFilterProcessesUrl("/login");
		return filter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
	
}
