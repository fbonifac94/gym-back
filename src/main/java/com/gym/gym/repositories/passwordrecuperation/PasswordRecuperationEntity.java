package com.gym.gym.repositories.passwordrecuperation;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "password_recuperation")
@Data
@Entity
public class PasswordRecuperationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "password_recuperation_id")
	private Long id;

	@Column(name = "password_recuperation_code")
	private String code;

	@Column(name = "password_recuperation_email")
	private String email;

	@Column(name = "password_recuperation_creation_date_time")
	private LocalDateTime creationDateTime;

	public PasswordRecuperationEntity(String code, String email, LocalDateTime creationDateTime) {
		this.code = code;
		this.email = email;
		this.creationDateTime = creationDateTime;
	}
}
