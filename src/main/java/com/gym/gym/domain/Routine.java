package com.gym.gym.domain;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Routine {

	private Long id;

	private String title;

	private LocalDate creationDate;

	private LocalDate modificationDate;

	private Customer customer;

	private Teacher teacher;

	private String aditionalInfo;
	
	private List<RoutineExercise> exercises;
}
