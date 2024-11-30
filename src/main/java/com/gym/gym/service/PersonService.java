package com.gym.gym.service;

import static com.gym.gym.errors.Errors.CUSTOMER_NOTFOUND_MSG_ERROR;

import org.springframework.stereotype.Service;

import com.gym.gym.domain.Person;
import com.gym.gym.repositories.person.PersonEntity;
import com.gym.gym.repositories.person.PersonRepository;
import com.gym.gym.service.mapper.PersonMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PersonService {

	private final PersonRepository personRepository;

	private final PersonMapper personMapper;

	public PersonService(PersonRepository personRepository, PersonMapper personMapper) {
		this.personRepository = personRepository;
		this.personMapper = personMapper;
	}

	public Person findById(Long id) {
		PersonEntity personEntity = this.personRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(CUSTOMER_NOTFOUND_MSG_ERROR.withParams(id)));
		return this.personMapper.entityToDomain(personEntity);
	}

}
