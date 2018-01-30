package io.gdiazs.commons.boot.security.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.gdiazs.commons.boot.security.authority.AuthorityDTO;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class UserDTO implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userName;
	
	private List<AuthorityDTO> Authorities;
	
	private String password;

	private Date lastPasswordResetDate;
	
	private Boolean accountNonExpired;
	
	private Boolean accountNonLocked;
	
	private Boolean credentialsNonExpired;
	
	private Boolean enabled;
	
	
	

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setAuthorities(List<AuthorityDTO> authorities) {
		Authorities = authorities;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return this.Authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}


	public String getUserName() {
		return userName;
	}

	public Date getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	public void setLastPasswordResetDate(Date lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
	}

	public Boolean getAccountNonExpired() {
		return accountNonExpired;
	}

	public Boolean getAccountNonLocked() {
		return accountNonLocked;
	}

	public Boolean getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserDTO userDTO = (UserDTO) o;

		if (userName != null ? !userName.equals(userDTO.userName) : userDTO.userName != null) return false;
		if (Authorities != null ? !Authorities.equals(userDTO.Authorities) : userDTO.Authorities != null) return false;
		if (password != null ? !password.equals(userDTO.password) : userDTO.password != null) return false;
		if (lastPasswordResetDate != null ? !lastPasswordResetDate.equals(userDTO.lastPasswordResetDate) : userDTO.lastPasswordResetDate != null)
			return false;
		if (accountNonExpired != null ? !accountNonExpired.equals(userDTO.accountNonExpired) : userDTO.accountNonExpired != null)
			return false;
		if (accountNonLocked != null ? !accountNonLocked.equals(userDTO.accountNonLocked) : userDTO.accountNonLocked != null)
			return false;
		if (credentialsNonExpired != null ? !credentialsNonExpired.equals(userDTO.credentialsNonExpired) : userDTO.credentialsNonExpired != null)
			return false;
		return enabled != null ? enabled.equals(userDTO.enabled) : userDTO.enabled == null;
	}

	@Override
	public int hashCode() {
		int result = userName != null ? userName.hashCode() : 0;
		result = 31 * result + (Authorities != null ? Authorities.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (lastPasswordResetDate != null ? lastPasswordResetDate.hashCode() : 0);
		result = 31 * result + (accountNonExpired != null ? accountNonExpired.hashCode() : 0);
		result = 31 * result + (accountNonLocked != null ? accountNonLocked.hashCode() : 0);
		result = 31 * result + (credentialsNonExpired != null ? credentialsNonExpired.hashCode() : 0);
		result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
		return result;
	}
}
