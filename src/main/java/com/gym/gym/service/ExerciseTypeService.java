package com.gym.gym.service;

import static com.gym.gym.errors.Errors.EXERCISETYPE_NOTFOUND_MSG_ERROR;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gym.gym.domain.ExerciseType;
import com.gym.gym.repositories.exercisetype.ExerciseTypeEntity;
import com.gym.gym.repositories.exercisetype.ExerciseTypeRepository;
import com.gym.gym.service.mapper.ExerciseTypeMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ExerciseTypeService {

	private final ExerciseTypeRepository exerciseTypeRepository;

	private final ExerciseTypeMapper exerciseTypeMapper;
	
	private final ExerciseService exerciseService;


	@Transactional
	public void create(String exerciseTypeName) {
		ExerciseTypeEntity entity = new ExerciseTypeEntity(exerciseTypeName);
		exerciseTypeRepository.saveAndFlush(entity);
	}

	@Transactional
	public void deleteById(Long exerciseTypeId) {
		ExerciseTypeEntity entity = exerciseTypeRepository.findById(exerciseTypeId).orElseThrow(
				() -> new EntityNotFoundException(EXERCISETYPE_NOTFOUND_MSG_ERROR.withParams(exerciseTypeId)));
		entity.getExercises().forEach(exercise -> exerciseService.deleteById(exercise.getId()));
		exerciseTypeRepository.delete(entity);
	}

	public ExerciseTypeEntity findById(Long exerciseTypeId) {
		return exerciseTypeRepository.findById(exerciseTypeId).orElseThrow(
				() -> new EntityNotFoundException(EXERCISETYPE_NOTFOUND_MSG_ERROR.withParams(exerciseTypeId)));
	}

	public List<ExerciseType> findAll() {
		return exerciseTypeMapper.mapEntityListToDomainList(this.findAllExerciseTypeEntities());
	}

	protected List<ExerciseTypeEntity> findAllExerciseTypeEntities() {
		return exerciseTypeRepository.findAll();
	}

}
