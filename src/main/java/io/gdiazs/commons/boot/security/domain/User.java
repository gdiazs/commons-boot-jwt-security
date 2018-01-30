package io.gdiazs.commons.boot.security.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_user", unique=true, nullable=false)
	private Integer idUser;

	@Column(name="created_at", nullable=false)
	private Timestamp createdAt;

	@Column(name="updated_at", nullable=false)
	private Timestamp updatedAt;

	@Column(name="user_account_non_expired", nullable=false)
	private Integer userAccountNonExpired;

	@Column(name="user_account_non_locked", nullable=false)
	private Integer userAccountNonLocked;

	@Column(name="user_credentials_non_expired", nullable=false)
	private Integer userCredentialsNonExpired;

	@Column(name="user_email", nullable=false, length=50)
	private String userEmail;

	@Column(name="user_enabled", nullable=false)
	private Integer userEnabled;

	@Column(name="user_last_password_reset_date", nullable=false)
	private Timestamp userLastPasswordResetDate;

	@Column(name="user_name", nullable=false, length=20)
	private String userName;

	@Column(name="user_password", nullable=false, length=100)
	private String userPassword;

	//bi-directional many-to-one association to UsersAuthority
	@OneToMany(mappedBy="user")
	private List<UsersAuthority> usersAuthorities;


	public User() {
	}

	public Integer getIdUser() {
		return this.idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
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

	public Integer getUserAccountNonExpired() {
		return this.userAccountNonExpired;
	}

	public void setUserAccountNonExpired(Integer userAccountNonExpired) {
		this.userAccountNonExpired = userAccountNonExpired;
	}

	public Integer getUserAccountNonLocked() {
		return this.userAccountNonLocked;
	}

	public void setUserAccountNonLocked(Integer userAccountNonLocked) {
		this.userAccountNonLocked = userAccountNonLocked;
	}

	public Integer getUserCredentialsNonExpired() {
		return this.userCredentialsNonExpired;
	}

	public void setUserCredentialsNonExpired(Integer userCredentialsNonExpired) {
		this.userCredentialsNonExpired = userCredentialsNonExpired;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Integer getUserEnabled() {
		return this.userEnabled;
	}

	public void setUserEnabled(Integer userEnabled) {
		this.userEnabled = userEnabled;
	}

	public Timestamp getUserLastPasswordResetDate() {
		return this.userLastPasswordResetDate;
	}

	public void setUserLastPasswordResetDate(Timestamp userLastPasswordResetDate) {
		this.userLastPasswordResetDate = userLastPasswordResetDate;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}



	public List<UsersAuthority> getUsersAuthorities() {
		return this.usersAuthorities;
	}

	public void setUsersAuthorities(List<UsersAuthority> usersAuthorities) {
		this.usersAuthorities = usersAuthorities;
	}

	public UsersAuthority addUsersAuthority(UsersAuthority usersAuthority) {
		getUsersAuthorities().add(usersAuthority);
		usersAuthority.setUser(this);

		return usersAuthority;
	}

	public UsersAuthority removeUsersAuthority(UsersAuthority usersAuthority) {
		getUsersAuthorities().remove(usersAuthority);
		usersAuthority.setUser(null);

		return usersAuthority;
	}


}