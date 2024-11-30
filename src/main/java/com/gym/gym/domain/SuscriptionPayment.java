package com.gym.gym.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.gym.gym.domain.enums.SuscriptionTimeUnits;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SuscriptionPayment {

	private Long id;

	private BigDecimal amount;

	private LocalDate date;

	private Long quantity;

	private SuscriptionTimeUnits susucriptionQuantityTimeUnits;

	private User user;
}
