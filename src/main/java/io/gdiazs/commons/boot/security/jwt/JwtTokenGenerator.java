package io.gdiazs.commons.boot.security.jwt;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.mobile.device.Device;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.gdiazs.commons.boot.security.user.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenGenerator {

	private final String AUDIENCE_UNKNOWN = "unknown";
	private final String AUDIENCE_WEB = "web";
	private final String AUDIENCE_MOBILE = "mobile";
	private final String AUDIENCE_TABLET = "tablet";

	private String secret = "default";

	private Long expiration = 604800L;
	
	private String issClaim;

	public JwtTokenGenerator(String secret, Long expiration) {
		super();
		this.secret = secret;
		this.expiration = expiration;
	}

	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	public Date getCreatedDateFromToken(String token) {
		Date created;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			created = new Date((Long) claims.get("created"));
		} catch (Exception e) {
			created = null;
		}
		return created;
	}

	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	public String getAudienceFromToken(String token) {
		String audience;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			audience = (String) claims.get("audience");
		} catch (Exception e) {
			audience = null;
		}
		return audience;
	}

	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			final String secret = Base64.getEncoder().encodeToString(this.secret.getBytes());
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	private Date generateCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + this.expiration * 1000);
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = this.getExpirationDateFromToken(token);
		return expiration.before(this.generateCurrentDate());
	}

	private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}

	private Boolean ignoreTokenExpiration(String token) {
		String audience = this.getAudienceFromToken(token);
		return (this.AUDIENCE_TABLET.equals(audience) || this.AUDIENCE_MOBILE.equals(audience)
		    || this.AUDIENCE_WEB.equals(audience) || this.AUDIENCE_UNKNOWN.equals(audience));
	}

	public String generateToken(UserDetails userDetails, Device device) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("sub", userDetails.getUsername());
		claims.put("iss", getIssClaim());
		claims.put("aud", getAudience(device));
		claims.put("audience", getAudience(device));
		claims.put("created", this.generateCurrentDate());
		claims.put("authorities", userDetails.getAuthorities());
		claims.put("upn", userDetails.getUsername());
		claims.put("groups", getGroups(userDetails));
		return this.generateToken(claims);
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("sub", userDetails.getUsername());
		claims.put("iss", getIssClaim());
		claims.put("aud", AUDIENCE_UNKNOWN);
		claims.put("audience", AUDIENCE_UNKNOWN);
		claims.put("created", this.generateCurrentDate());
		claims.put("authorities", userDetails.getAuthorities());
		claims.put("upn", userDetails.getUsername());
		claims.put("groups", getGroups(userDetails));
		return this.generateToken(claims);
	}

	private String generateToken(Map<String, Object> claims) {
		final String secret = Base64.getEncoder().encodeToString(this.secret.getBytes());

		return Jwts.builder().setClaims(claims).setExpiration(this.generateExpirationDate())
		    .signWith(SignatureAlgorithm.HS256, secret).compact();

	}

	public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
		final Date created = this.getCreatedDateFromToken(token);
		return (!(this.isCreatedBeforeLastPasswordReset(created, lastPasswordReset))
		    && (!(this.isTokenExpired(token)) || this.ignoreTokenExpiration(token)));
	}

	public String refreshToken(String token) {
		String refreshedToken;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			claims.put("created", this.generateCurrentDate());
			refreshedToken = this.generateToken(claims);
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		UserDTO user = (UserDTO) userDetails;
		final String username = this.getUsernameFromToken(token);
		final Date created = this.getCreatedDateFromToken(token);

		boolean createdBeforeLastPasswordRest = this.isCreatedBeforeLastPasswordReset(created,
		    user.getLastPasswordResetDate());
		boolean expired = this.isTokenExpired(token);
		return (username.equals(user.getUserName()) && !(expired) && !(createdBeforeLastPasswordRest));
	}

	private String getAudience(Device device) {
		if (device.isNormal())
			return this.AUDIENCE_WEB;

		if (device.isMobile())
			return this.AUDIENCE_MOBILE;

		if (device.isTablet())
			return this.AUDIENCE_TABLET;

		return this.AUDIENCE_UNKNOWN;

	}
	
	
	private static List<String> getGroups(UserDetails userDetails) {
		List<String> groups = new ArrayList<>();
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		authorities.forEach(auth -> groups.add(auth.getAuthority()));
		return groups;
	}

	public String getIssClaim() {
		return issClaim;
	}

	public void setIssClaim(String issClaim) {
		this.issClaim = issClaim;
	}
	
	

}
