package com.gym.gym.repositories.person;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "person")
@Entity
public class PersonEntity {

	@Id
	@Column(name = "person_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "person_first_name")
	private String firstName;

	@Column(name = "person_last_name")
	private String lastName;

	@Column(name = "person_phone")
	private String phone;

	@Column(name = "person_born_date")
	private LocalDate bornDate;

	@Column(name = "person_country")
	private String country;

	@Column(name = "person_city")
	private String city;

	@Column(name = "person_district")
	private String district;

	@Column(name = "person_address")
	private String address;

	@Column(name = "person_document_type")
	private String documentType;

	@Column(name = "person_document_number")
	private String documetNumber;

	public PersonEntity(String firstName, String lastName, String phone, LocalDate bornDate, String country,
			String city, String district, String address, String documentType, String documentNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
		this.phone = phone;
		this.bornDate = bornDate;
		this.country = country;
		this.city = city;
		this.district = district;
		this.address = address;
		this.documentType = documentType;
		this.documetNumber = documentNumber;
	}
}
