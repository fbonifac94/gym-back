package com.gym.gym.controllers.exercise;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gym.gym.domain.Exercise;
import com.gym.gym.domain.ExercisesByExerciseType;
import com.gym.gym.service.ExerciseService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/exercises")
public class ExerciseController {

	private final ExerciseService exerciseService;

	public ExerciseController(ExerciseService exerciseService) {
		this.exerciseService = exerciseService;
	}

	@PostMapping
	public ResponseEntity<Void> createExercise(@Valid @RequestBody CreateExerciseRequest request) {
		this.exerciseService.create(request.getExerciseName(), request.getExerciseTypeId());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteExercise(@PathVariable("id") Long id) {
		this.exerciseService.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/exercises")
	public ResponseEntity<List<Exercise>> getAllExercises() {
		List<Exercise> exerciseTypes = this.exerciseService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(exerciseTypes);
	}

	@GetMapping("/type")
	public ResponseEntity<List<ExercisesByExerciseType>> getAllExerciseByExerciseType() {
		List<ExercisesByExerciseType> exercises = this.exerciseService.findAllExercisesByExerciseType();
		return ResponseEntity.status(HttpStatus.OK).body(exercises);
	}

	@GetMapping("/exercise_type/{exercise_type_id}")
	public ResponseEntity<List<Exercise>> getAllExercisesFromExerciseType(
			@PathVariable("exercise_type_id") Long exercise_type_id) {
		List<Exercise> exerciseTypes = this.exerciseService.findByExerciseTypeId(exercise_type_id);
		return ResponseEntity.status(HttpStatus.OK).body(exerciseTypes);
	}
}
