package com.gym.gym.controllers.routine;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateRoutineRequest {

	private List<ExerciseByRoutineRequest> exercises;

	private String aditionalInfo;

}
