package com.gym.gym.domain;

import java.util.List;

import lombok.Getter;

@Getter
public class ExercisesByExerciseType {

	private ExerciseType exerciseType;

	private List<ExerciseWithoutExerciseType> exercises;

	public ExercisesByExerciseType(ExerciseType exerciseType, List<Exercise> exercises) {
		this.exerciseType = exerciseType;
		this.exercises = exercises.stream().map(ExerciseWithoutExerciseType::new).toList();
	}
}
