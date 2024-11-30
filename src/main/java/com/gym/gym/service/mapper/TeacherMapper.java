package com.gym.gym.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.gym.gym.domain.Teacher;
import com.gym.gym.repositories.teacher.TeacherEntity;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TeacherMapper {

	Teacher entityToDomain(TeacherEntity entity);

	List<Teacher> entityListToDomainList(List<TeacherEntity> entity);
}
