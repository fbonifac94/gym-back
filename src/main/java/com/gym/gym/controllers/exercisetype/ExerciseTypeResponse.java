package com.gym.gym.controllers.exercisetype;

import com.gym.gym.domain.ExerciseType;

import lombok.Getter;

@Getter
public class ExerciseTypeResponse {

	private Long id;

	private String name;

	public ExerciseTypeResponse(ExerciseType exerciseType) {
		this.id = exerciseType.getId();
		this.name = exerciseType.getName();
	}
}
