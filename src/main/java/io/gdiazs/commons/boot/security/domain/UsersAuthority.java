package io.gdiazs.commons.boot.security.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the users_authorities database table.
 * 
 */
@Entity
@Table(name="users_authorities")
@NamedQuery(name="UsersAuthority.findAll", query="SELECT u FROM UsersAuthority u")
public class UsersAuthority implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UsersAuthorityPK id;

	@Column(name="created_at", nullable=false)
	private Timestamp createdAt;

	@Column(name="updated_at", nullable=false)
	private Timestamp updatedAt;

	//bi-directional many-to-one association to Authority
	@ManyToOne
	@JoinColumn(name="authoritie_id", nullable=false, insertable=false, updatable=false)
	private Authority authority;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false, insertable=false, updatable=false)
	private User user;

	public UsersAuthority() {
	}

	public UsersAuthorityPK getId() {
		return this.id;
	}

	public void setId(UsersAuthorityPK id) {
		this.id = id;
	}

	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Authority getAuthority() {
		return this.authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}