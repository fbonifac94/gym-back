package com.gym.gym.controllers.suscriptionpayment;

import java.math.BigDecimal;

import com.gym.gym.domain.enums.SuscriptionTimeUnits;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostSuscriptionRequest {

	private BigDecimal amount;

	private Long suscriptionQuantity;

	private SuscriptionTimeUnits susucriptionQuantityTimeUnits;
}
