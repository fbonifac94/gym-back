package com.gym.gym.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.gym.gym.domain.Day;
import com.gym.gym.repositories.day.DayEntity;

@Mapper(componentModel = "spring")
public interface DayMapper {

	Day entityToDomain(DayEntity entity);

	List<Day> entityListToDomainList(List<DayEntity> entity);
}
