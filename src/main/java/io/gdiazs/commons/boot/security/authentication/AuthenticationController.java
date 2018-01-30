package io.gdiazs.commons.boot.security.authentication;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.gdiazs.commons.boot.security.jwt.JwtTokenGenerator;
import io.gdiazs.commons.boot.security.user.UserDTO;
import io.gdiazs.commons.boot.security.web.CommonsWebSecurityConstants;



@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenGenerator tokenGenerator;

	@Autowired
	@Qualifier("userDetailsServiceImpl")
	private UserDetailsService userDetailsService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest,
	    Device device) throws AuthenticationException {

		Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
		    authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		String token = this.tokenGenerator.generateToken(userDetails, device);

		return ResponseEntity.ok(new AuthenticationResponse(token));
	}

	@RequestMapping(value = "refresh", method = RequestMethod.GET)
	public ResponseEntity<?> authenticationRequest(HttpServletRequest request) {
		String token = request.getHeader(CommonsWebSecurityConstants.TOKEN_HEADER);
		String username = this.tokenGenerator.getUsernameFromToken(token);
		UserDTO user = (UserDTO) this.userDetailsService.loadUserByUsername(username);

		Calendar start = Calendar.getInstance();
		start.setTimeInMillis(user.getLastPasswordResetDate().getTime());

		if (this.tokenGenerator.canTokenBeRefreshed(token, start.getTime())) {
			String refreshedToken = this.tokenGenerator.refreshToken(token);
			return ResponseEntity.ok(new AuthenticationResponse(refreshedToken));
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

}
