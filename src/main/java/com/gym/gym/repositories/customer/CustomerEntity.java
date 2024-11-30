package com.gym.gym.repositories.customer;

import static jakarta.persistence.FetchType.LAZY;

import com.gym.gym.repositories.user.UserEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "customer")
@Data
@Entity
public class CustomerEntity {

	@Id
	@Column(name = "customer_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private UserEntity user;

	public CustomerEntity(UserEntity user) {
		this.user = user;
	}

}
