package io.gdiazs.commons.boot.security.authentication;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.context.support.WebApplicationContextUtils;

import io.gdiazs.commons.boot.security.jwt.JwtTokenGenerator;
import io.gdiazs.commons.boot.security.web.CommonsWebSecurityConstants;




public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

	final static Logger logger = Logger.getLogger(AuthenticationTokenFilter.class);

	@Autowired
	private JwtTokenGenerator tokenUtils;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		logger.debug("Filtering headers...");

		tokenUtils = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext())
				.getBean(JwtTokenGenerator.class);
		userDetailsService = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext())
				.getBean(UserDetailsService.class);

		HttpServletResponse resp = (HttpServletResponse) response;
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
		resp.setHeader("Access-Control-Max-Age", "3600");
		resp.setHeader("Access-Control-Allow-Headers",
				"Origin, X-Requested-With, Content-Type, Accept, " + CommonsWebSecurityConstants.TOKEN_HEADER);

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		// Obtiene de las cabeceras el token
		String authToken = httpRequest.getHeader(CommonsWebSecurityConstants.TOKEN_HEADER);

		logger.debug(CommonsWebSecurityConstants.TOKEN_HEADER + ": " + authToken);

		// Obtiene del token el username
		String username = this.tokenUtils.getUsernameFromToken(authToken);

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			if (this.tokenUtils.validateToken(authToken, userDetails)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		chain.doFilter(request, response);
	}

}
