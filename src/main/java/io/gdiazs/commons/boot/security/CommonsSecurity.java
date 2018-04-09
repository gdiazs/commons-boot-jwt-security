package io.gdiazs.commons.boot.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.gdiazs.commons.boot.security.jwt.JwtTokenGenerator;

@SpringBootApplication
public class CommonsSecurity {

	@Value("${token.secret}")
	private String secret;

	@Value("${token.expiration}")
	private String expiration;

	@Bean
	public JwtTokenGenerator tokenUtils() {
		return new JwtTokenGenerator(secret, new Long(expiration));
	}


}
