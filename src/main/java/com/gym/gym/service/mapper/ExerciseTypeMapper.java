package com.gym.gym.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.gym.gym.domain.ExerciseType;
import com.gym.gym.repositories.exercisetype.ExerciseTypeEntity;

@Mapper(componentModel = "spring")
public interface ExerciseTypeMapper {

	public ExerciseType mapEntityToDomain(ExerciseTypeEntity entity);

	public List<ExerciseType> mapEntityListToDomainList(List<ExerciseTypeEntity> listEntity);

}
