package io.gdiazs.commons.boot.security.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the authorities database table.
 * 
 */
@Entity
@Table(name="authorities")
@NamedQuery(name="Authority.findAll", query="SELECT a FROM Authority a")
public class Authority implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_authoritie", unique=true, nullable=false)
	private Integer idAuthoritie;

	@Column(name="authority_description", nullable=false, length=100)
	private String authorityDescription;

	@Column(name="authority_enabled", nullable=false)
	private Integer authorityEnabled;

	@Column(name="authority_name", nullable=false, length=40)
	private String authorityName;

	@Column(name="created_at", nullable=false)
	private Timestamp createdAt;

	@Column(name="updated_at", nullable=false)
	private Timestamp updatedAt;

	//bi-directional many-to-one association to UsersAuthority
	@OneToMany(mappedBy="authority")
	private List<UsersAuthority> usersAuthorities;

	public Authority() {
	}

	public Integer getIdAuthoritie() {
		return this.idAuthoritie;
	}

	public void setIdAuthoritie(Integer idAuthoritie) {
		this.idAuthoritie = idAuthoritie;
	}

	public String getAuthorityDescription() {
		return this.authorityDescription;
	}

	public void setAuthorityDescription(String authorityDescription) {
		this.authorityDescription = authorityDescription;
	}

	public Integer getAuthorityEnabled() {
		return this.authorityEnabled;
	}

	public void setAuthorityEnabled(Integer authorityEnabled) {
		this.authorityEnabled = authorityEnabled;
	}

	public String getAuthorityName() {
		return this.authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
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

	public List<UsersAuthority> getUsersAuthorities() {
		return this.usersAuthorities;
	}

	public void setUsersAuthorities(List<UsersAuthority> usersAuthorities) {
		this.usersAuthorities = usersAuthorities;
	}

	public UsersAuthority addUsersAuthority(UsersAuthority usersAuthority) {
		getUsersAuthorities().add(usersAuthority);
		usersAuthority.setAuthority(this);

		return usersAuthority;
	}

	public UsersAuthority removeUsersAuthority(UsersAuthority usersAuthority) {
		getUsersAuthorities().remove(usersAuthority);
		usersAuthority.setAuthority(null);

		return usersAuthority;
	}

}