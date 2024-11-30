package com.gym.gym.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Exercise {

	private Long id;

	private String name;

	private ExerciseType exerciseType;
}
