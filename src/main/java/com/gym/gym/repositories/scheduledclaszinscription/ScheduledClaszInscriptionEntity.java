package com.gym.gym.repositories.scheduledclaszinscription;

import com.gym.gym.repositories.customer.CustomerEntity;
import com.gym.gym.repositories.scheduledclasz.ScheduledClaszEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "scheduled_class_inscription")
@Data
@Entity
public class ScheduledClaszInscriptionEntity {

	@Id
	@Column(name = "scheduled_class_inscription_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "scheduled_class_id", nullable = false)
	private ScheduledClaszEntity scheduledClasz;

	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private CustomerEntity customer;

	public ScheduledClaszInscriptionEntity(ScheduledClaszEntity scheduledClasz, CustomerEntity customer) {
		this.scheduledClasz = scheduledClasz;
		this.customer = customer;
	}

}
