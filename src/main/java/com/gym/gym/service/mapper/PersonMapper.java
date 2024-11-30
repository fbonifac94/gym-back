package com.gym.gym.service.mapper;

import org.mapstruct.Mapper;

import com.gym.gym.domain.Person;
import com.gym.gym.repositories.person.PersonEntity;

@Mapper(componentModel = "spring")
public interface PersonMapper {

	Person entityToDomain(PersonEntity personEntity);
}
