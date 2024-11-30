package com.gym.gym.controllers.person;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gym.gym.domain.Person;
import com.gym.gym.service.PersonService;

@Controller
@RequestMapping("/persons")
public class PersonController {
	
	private final PersonService personService;

	public PersonController(PersonService personService) {
		this.personService = personService;
	}

	@GetMapping("/{personId}")
	public ResponseEntity<Person> getPersonById(@PathVariable("personId") Long id) {
		Person person = this.personService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(person);
	}
}
