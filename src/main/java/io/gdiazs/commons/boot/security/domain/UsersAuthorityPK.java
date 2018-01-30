package io.gdiazs.commons.boot.security.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the users_authorities database table.
 * 
 */
@Embeddable
public class UsersAuthorityPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="user_id", insertable=false, updatable=false, unique=true, nullable=false)
	private Integer userId;

	@Column(name="authoritie_id", insertable=false, updatable=false, unique=true, nullable=false)
	private Integer authoritieId;

	public UsersAuthorityPK() {
	}
	public Integer getUserId() {
		return this.userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getAuthoritieId() {
		return this.authoritieId;
	}
	public void setAuthoritieId(Integer authoritieId) {
		this.authoritieId = authoritieId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UsersAuthorityPK)) {
			return false;
		}
		UsersAuthorityPK castOther = (UsersAuthorityPK)other;
		return 
			this.userId.equals(castOther.userId)
			&& this.authoritieId.equals(castOther.authoritieId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.authoritieId.hashCode();
		
		return hash;
	}
}