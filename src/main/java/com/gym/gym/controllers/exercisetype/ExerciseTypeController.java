package com.gym.gym.controllers.exercisetype;

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

import com.gym.gym.domain.ExerciseType;
import com.gym.gym.service.ExerciseTypeService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/exercises_types")
public class ExerciseTypeController {

	private final ExerciseTypeService exerciseTypeService;

	public ExerciseTypeController(ExerciseTypeService exerciseTypeService) {
		this.exerciseTypeService = exerciseTypeService;
	}

	@PostMapping
	public ResponseEntity<Void> createExerciseType(@Valid @RequestBody CreateExerciseTypeRequest request) {
		this.exerciseTypeService.create(request.getExeciseTypeName());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteExerciseType(@PathVariable("id") Long id) {
		this.exerciseTypeService.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping
	public ResponseEntity<List<ExerciseType>> getAllExerciseTypes() {
		List<ExerciseType> exerciseTypes = this.exerciseTypeService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(exerciseTypes);
	}
}
