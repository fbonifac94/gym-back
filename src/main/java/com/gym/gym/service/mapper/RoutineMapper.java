package com.gym.gym.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.gym.gym.domain.Routine;
import com.gym.gym.repositories.routine.RoutineEntity;

@Mapper(componentModel = "spring", uses = { TeacherMapper.class, CustomerMapper.class, RoutineExerciseMapper.class })
public interface RoutineMapper {

	Routine entityToDomain(RoutineEntity entity);

	List<Routine> entityListToDomainList(List<RoutineEntity> entity);
}
