package com.gym.gym.service;

import static com.gym.gym.errors.Errors.EXERCISES_IDS_NOT_FOUND;
import static com.gym.gym.errors.Errors.EXERCISETYPE_NOTFOUND_MSG_ERROR;
import static com.gym.gym.errors.Errors.EXERCISE_NOTFOUND_MSG_ERROR;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gym.gym.domain.Exercise;
import com.gym.gym.domain.ExerciseType;
import com.gym.gym.domain.ExercisesByExerciseType;
import com.gym.gym.repositories.exercise.ExerciseEntity;
import com.gym.gym.repositories.exercise.ExerciseRepository;
import com.gym.gym.repositories.exercisetype.ExerciseTypeEntity;
import com.gym.gym.repositories.exercisetype.ExerciseTypeRepository;
import com.gym.gym.service.mapper.ExerciseMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ExerciseService {

	private final ExerciseRepository exerciseRepository;

	private final ExerciseMapper exerciseMapper;

	private final ExerciseTypeRepository exerciseTypeRepository;

	private final RoutineService routineService;

	@Transactional
	public void create(String exerciseName, Long exerciseTypeId) {
		ExerciseTypeEntity exerciseType = exerciseTypeRepository.findById(exerciseTypeId).orElseThrow(
				() -> new EntityNotFoundException(EXERCISETYPE_NOTFOUND_MSG_ERROR.withParams(exerciseTypeId)));
		ExerciseEntity exercise = new ExerciseEntity(exerciseName, exerciseType);
		exerciseRepository.saveAndFlush(exercise);
	}

	@Transactional
	public void deleteById(Long exerciseId) {
		ExerciseEntity exercise = exerciseRepository.findById(exerciseId)
				.orElseThrow(() -> new EntityNotFoundException(EXERCISE_NOTFOUND_MSG_ERROR.withParams(exerciseId)));
		routineService.deleteRoutineByExerciseId(exerciseId);
		exerciseRepository.delete(exercise);
	}

	public List<Exercise> findByExerciseTypeId(Long exerciseTypeId) {
		return exerciseMapper.mapEntityListToDomainList(exerciseRepository.findByExerciseTypeId(exerciseTypeId));
	}

	public List<ExerciseEntity> findByIds(List<Long> ids) {
		List<ExerciseEntity> exercises = exerciseRepository.findAllById(ids);
		if (exercises.size() != ids.size()) {
			List<Long> findedExercisesIds = exercises.stream().map(ExerciseEntity::getId).collect(Collectors.toList());
			ids.removeAll(ids);
			throw new RuntimeException(EXERCISES_IDS_NOT_FOUND.withParams(findedExercisesIds.toString()));
		}
		return exercises;
	}

	public List<Exercise> findAll() {
		return exerciseMapper.mapEntityListToDomainList(exerciseRepository.findAll());

	}

	public List<ExercisesByExerciseType> findAllExercisesByExerciseType() {
		List<ExerciseTypeEntity> exerciseTypes = exerciseTypeRepository.findAll();
		List<ExerciseEntity> exercises = exerciseRepository.findAll();
		return exerciseTypes.stream().map(elem -> {
			return new ExercisesByExerciseType(new ExerciseType(elem.getId(), elem.getName()),
					exerciseMapper.mapEntityListToDomainList(exercises.stream()
							.filter(elem_ -> elem_.getExerciseType().getId().equals(elem.getId())).toList()));
		}).toList();
	}
}
