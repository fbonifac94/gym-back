package com.gym.gym.controllers.exercise;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateExerciseRequest {

	private Long exerciseTypeId;

	private String exerciseName;
}
