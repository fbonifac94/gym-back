package com.gym.gym.domain;

import java.time.LocalDateTime;

import com.gym.gym.domain.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ScheduledClasz {

	private Long id;

	private LocalDateTime initDateTime;

	private LocalDateTime endDateTime;

	private Integer actualClassPersonsAmount;
	
	private Integer totalClassPersonsAmount;

	private Clasz clasz;

	private Teacher teacher;

	private Status status;
}
