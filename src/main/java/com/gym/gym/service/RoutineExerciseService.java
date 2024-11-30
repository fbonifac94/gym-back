package com.gym.gym.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gym.gym.controllers.routine.ExerciseByRoutineRequest;
import com.gym.gym.repositories.exercise.ExerciseRepository;
import com.gym.gym.repositories.routine.RoutineEntity;
import com.gym.gym.repositories.routineexercise.RoutineExerciseEntity;
import com.gym.gym.repositories.routineexercise.RoutineExerciseRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoutineExerciseService {

	private final RoutineExerciseRepository routineExerciseRepository;

	private final ExerciseRepository exerciseRepository;

	@Transactional
	public void createRoutineExercise(RoutineEntity routineEntity, List<ExerciseByRoutineRequest> exercises) {
		List<Long> exercisesId = exercises.stream().map(ExerciseByRoutineRequest::getExerciseId)
				.collect(Collectors.toList());

		List<RoutineExerciseEntity> routineExercises = exerciseRepository.findAllById(exercisesId).stream()
				.map(exercise -> {
					ExerciseByRoutineRequest exerciseInRoutine = exercises.stream()
							.filter(ex -> ex.getExerciseId().equals(exercise.getId())).findFirst()
							.orElseThrow(() -> new EntityNotFoundException(
									String.format("No se encontró el ejercicio con id %", exercise.getId())));

					return new RoutineExerciseEntity(routineEntity, exercise, exerciseInRoutine.getRepetitions(),
							exerciseInRoutine.getSeries(), exerciseInRoutine.getAditionalInfo());
				}).collect(Collectors.toList());

		routineExerciseRepository.saveAllAndFlush(routineExercises);
	}

	@Transactional
	public void deleteRoutineExercises(List<RoutineExerciseEntity> routineExercisesEntityList) {
		List<Long> ids = routineExercisesEntityList.stream().map(RoutineExerciseEntity::getId).toList();
		routineExerciseRepository.deleteRoutineExercises(ids);
	}
}
