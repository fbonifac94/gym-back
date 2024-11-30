package com.gym.gym.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RoutineExercise {

	private Long id;

	private Exercise exercise;

	private Integer repetitions;

	private Integer series;

	private String aditionalInfo;
}
