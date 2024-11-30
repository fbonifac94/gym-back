package com.gym.gym.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.gym.gym.domain.Schedule;
import com.gym.gym.repositories.schedule.ScheduleEntity;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

	Schedule entityToDomain(ScheduleEntity entity);

	List<Schedule> entityListToDomainList(List<ScheduleEntity> entity);
}
