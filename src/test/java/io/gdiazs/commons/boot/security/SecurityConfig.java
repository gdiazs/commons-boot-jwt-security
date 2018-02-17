package io.gdiazs.commons.boot.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import io.gdiazs.commons.boot.security.web.CommonsWebSecurityAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends CommonsWebSecurityAdapter{

	@Override
	public void configure(WebSecurity web) throws Exception {

		super.configure(web);
		web.ignoring().antMatchers("/unsecure");
	}

	
}
