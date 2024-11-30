package com.gym.gym.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.gym.gym.domain.ScheduledClaszInscription;
import com.gym.gym.repositories.scheduledclaszinscription.ScheduledClaszInscriptionEntity;

@Mapper(componentModel = "spring", uses = { CustomerMapper.class, ScheduledClaszMapper.class })
public interface ScheduledClaszInscriptionMapper {

	ScheduledClaszInscription entityToDomain(ScheduledClaszInscriptionEntity entity);

	List<ScheduledClaszInscription> entityListToDomainList(List<ScheduledClaszInscriptionEntity> entity);
}
