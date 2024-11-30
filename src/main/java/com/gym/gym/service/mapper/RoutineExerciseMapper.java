package com.gym.gym.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.gym.gym.domain.RoutineExercise;
import com.gym.gym.repositories.routineexercise.RoutineExerciseEntity;

@Mapper(componentModel = "spring", uses = { TeacherMapper.class, CustomerMapper.class, ExerciseMapper.class })
public interface RoutineExerciseMapper {

	RoutineExercise entityToDomain(RoutineExerciseEntity entity);

	List<RoutineExercise> entityListToDomainList(List<RoutineExerciseEntity> entity);
}
