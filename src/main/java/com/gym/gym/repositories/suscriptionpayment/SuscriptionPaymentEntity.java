package com.gym.gym.repositories.suscriptionpayment;

import static jakarta.persistence.FetchType.LAZY;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.gym.gym.domain.enums.SuscriptionTimeUnits;
import com.gym.gym.repositories.user.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "suscription_payment")
@Data
@Entity
public class SuscriptionPaymentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "suscription_payment_id")
	private Long id;

	@Column(name = "suscription_payment_amount")
	private BigDecimal amount;

	@Column(name = "suscription_payment_date")
	private LocalDateTime date;

	@Column(name = "suscription_payment_quantity")
	private Long quantity;

	@Column(name = "suscription_payment_quantity_time_units")
	@Enumerated(EnumType.STRING)
	private SuscriptionTimeUnits susucriptionQuantityTimeUnits;

	@Column(name = "suscription_payment_new_expire_date")
	private LocalDate newExpireDate;

	@Column(name = "suscription_payment_old_expire_date")
	private LocalDate oldExpireDate;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity user;

	public SuscriptionPaymentEntity(BigDecimal amount, LocalDateTime date, Long quantity,
			SuscriptionTimeUnits susucriptionQuantityTimeUnits, LocalDate oldExpireDate, LocalDate newExpireDate,
			UserEntity user) {
		this.amount = amount;
		this.date = date;
		this.quantity = quantity;
		this.susucriptionQuantityTimeUnits = susucriptionQuantityTimeUnits;
		this.newExpireDate = newExpireDate;
		this.oldExpireDate = oldExpireDate;
		this.user = user;
	}
}
