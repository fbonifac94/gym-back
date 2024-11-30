package com.gym.gym.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.gym.gym.domain.Clasz;
import com.gym.gym.repositories.clasz.ClaszEntity;

@Mapper(componentModel = "spring", uses = { ScheduleMapper.class, DayMapper.class, TeacherMapper.class })
public interface ClaszMapper {

	Clasz entityToDomain(ClaszEntity entity);

	List<Clasz> entityListToDomainList(List<ClaszEntity> entity);
}
