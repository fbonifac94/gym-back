package com.gym.gym.repositories.user;

import static com.gym.gym.domain.enums.Role.CUSTOMER;
import static com.gym.gym.domain.enums.UserStatus.HA;
import static jakarta.persistence.FetchType.LAZY;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gym.gym.domain.enums.UserStatus;
import com.gym.gym.repositories.person.PersonEntity;
import com.gym.gym.repositories.role.RoleEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
@Data
@Entity
public class UserEntity implements UserDetails {

	private static final long serialVersionUID = -6956670329780860384L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Column(name = "user_email")
	private String email;

	@Column(name = "user_password")
	private String password;

	@Column(name = "user_is_first_login")
	private Boolean firstLogin;

	@Column(name = "user_expire_date")
	private LocalDate expireDate;

	@Column(name = "user_status")
	@Enumerated(EnumType.STRING)
	private UserStatus status;

	@OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "person_id")
	private PersonEntity person;

	@OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "role_id")
	private RoleEntity role;

	public UserEntity(String email, String password, Boolean firstLogin, UserStatus status, LocalDate expireDate,
			PersonEntity person, RoleEntity role) {
		this.email = email;
		this.password = password;
		this.firstLogin = firstLogin;
		this.status = status;
		this.expireDate = expireDate;
		this.person = person;
		this.role = role;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(role.getName().name()));
		return authorities;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		if (CUSTOMER.equals(this.getRole().getName())) {
			return (Objects.isNull(this.getExpireDate())) ? false : this.getExpireDate().isAfter(LocalDate.now());
		}
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return HA.equals(this.getStatus());
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
