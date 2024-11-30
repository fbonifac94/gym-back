package com.gym.gym.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gym.gym.domain.Exercise;
import com.gym.gym.repositories.exercise.ExerciseEntity;

@Mapper(componentModel = "spring", uses = { ExerciseTypeMapper.class })
public interface ExerciseMapper {

	public Exercise mapEntityToDomain(ExerciseEntity entity);

	public List<Exercise> mapEntityListToDomainList(List<ExerciseEntity> listEntity);

	@Mapping(source = "exerciseType", target = "exerciseType", ignore = true)
	public List<Exercise> mapEntityListToDomainListWithoutExerciseType(List<ExerciseEntity> listEntity);
}
