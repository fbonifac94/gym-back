package com.gym.gym.domain;

import lombok.Getter;

@Getter
public class ExerciseWithoutExerciseType {

	private Long id;

	private String name;

	public ExerciseWithoutExerciseType(Exercise exercise) {
		this.id = exercise.getId();
		this.name = exercise.getName();
	}
}
