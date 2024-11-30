package com.gym.gym.domain;

import com.gym.gym.domain.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Clasz {

	private Long id;

	private Day day;

	private Schedule initSchedule;

	private Schedule endSchedule;

	private Teacher teacher;
	
	private String name;

	private Integer classPersonsAmount;

	private Status status;
}
