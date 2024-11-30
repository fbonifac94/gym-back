package com.gym.gym.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.gym.gym.domain.ScheduledClasz;
import com.gym.gym.repositories.scheduledclasz.ScheduledClaszEntity;

@Mapper(componentModel = "spring", uses = { ClaszMapper.class, TeacherMapper.class })
public interface ScheduledClaszMapper {

	ScheduledClasz entityToDomain(ScheduledClaszEntity entity);

	List<ScheduledClasz> entityListToDomainList(List<ScheduledClaszEntity> entity);
}
