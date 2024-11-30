package com.gym.gym.controllers.routine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExerciseByRoutineRequest {

	private Long exerciseId;

	private Integer repetitions;

	private Integer series;

	private String aditionalInfo;
}
