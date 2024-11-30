package com.gym.gym.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ScheduledClaszInscription {

	private Long id;

	private ScheduledClasz scheduledClasz;

	private Customer customer;
}
