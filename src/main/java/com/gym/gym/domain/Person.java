package com.gym.gym.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {

	private Long id;

	private String firstName;

	private String lastName;

	private String phone;

	private LocalDate bornDate;

	private String country;

	private String city;

	private String district;

	private String address;

	private String documentType;

	private String documetNumber;
}
