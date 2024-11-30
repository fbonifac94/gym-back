package com.gym.gym.controllers.user;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdatePersonRequest {

	private String firstName;
	private String lastName;
	private String phone;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate bornDate;
	private String country;
	private String city;
	private String district;
	private String address;
	private String documentType;
	private String documetNumber;
}
