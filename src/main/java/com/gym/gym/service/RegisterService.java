package com.gym.gym.service;

import static com.gym.gym.domain.enums.Role.ADMIN;
import static com.gym.gym.domain.enums.Role.CUSTOMER;
import static com.gym.gym.domain.enums.Role.TEACHER;
import static com.gym.gym.domain.enums.UserStatus.HA;
import static java.lang.Boolean.TRUE;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.gym.gym.controllers.register.RegisterRequest;
import com.gym.gym.domain.enums.Role;
import com.gym.gym.exception.RegisterProcessException;
import com.gym.gym.repositories.admin.AdminEntity;
import com.gym.gym.repositories.admin.AdminRepository;
import com.gym.gym.repositories.customer.CustomerEntity;
import com.gym.gym.repositories.customer.CustomerRepository;
import com.gym.gym.repositories.person.PersonEntity;
import com.gym.gym.repositories.role.RoleEntity;
import com.gym.gym.repositories.role.RoleRepository;
import com.gym.gym.repositories.teacher.TeacherEntity;
import com.gym.gym.repositories.teacher.TeacherRepository;
import com.gym.gym.repositories.user.UserEntity;
import com.gym.gym.repositories.user.UserRepository;
import com.gym.gym.util.EmailUtils;
import com.gym.gym.util.Utils;

import jakarta.transaction.Transactional;

@Service
public class RegisterService {

	private final UserRepository userRepository;

	private final TeacherRepository teacherRepository;

	private final CustomerRepository customerRepository;

	private final AdminRepository adminRepository;

	private final RoleRepository roleRepository;

	private final PasswordEncoder passwordEncoder;

	private final EmailUtils emailUtils;

	public RegisterService(UserRepository userRepository, TeacherRepository teacherRepository,
			CustomerRepository customerRepository, AdminRepository adminRepository, PasswordEncoder passwordEncoder,
			RoleRepository roleRepository, EmailUtils emailUtils) {
		this.userRepository = userRepository;
		this.teacherRepository = teacherRepository;
		this.customerRepository = customerRepository;
		this.adminRepository = adminRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.emailUtils = emailUtils;
	}

	@Transactional
	public void registerAdmin(RegisterRequest request) {
		UserEntity user = createUser(request, ADMIN);
		AdminEntity admin = new AdminEntity(user);
		this.adminRepository.saveAndFlush(admin);
	}

	@Transactional
	public void registerTeacher(RegisterRequest request) {
		UserEntity user = createUser(request, TEACHER);
		TeacherEntity teacher = new TeacherEntity(user);
		this.teacherRepository.saveAndFlush(teacher);
	}

	@Transactional
	public void registerCustomer(RegisterRequest request) {
		UserEntity user = createUser(request, CUSTOMER);
		CustomerEntity customer = new CustomerEntity(user);
		this.customerRepository.saveAndFlush(customer);
	}

	@Transactional
	public UserEntity createUser(RegisterRequest request, Role role) {
		String email = request.getEmail();

		validateEmailInUse(email);

		validateDocumentInUse(request.getDocumentType(), request.getDocumentNumber());

		RoleEntity roleEntity = roleRepository.findByName(role);

		String password = Utils.generateRandomAlphanumericString(10);

		PersonEntity person = new PersonEntity(request.getFirstName(), request.getLastName(), request.getPhone(),
				request.getBornDate(), request.getCountry(), request.getCity(), request.getDistrict(),
				request.getAddress(), request.getDocumentType(), request.getDocumentNumber());

		String hashedPassword = passwordEncoder.encode(password);
		sendRegistrationEmail(request.getEmail(), request.getFirstName(), request.getLastName(), password);

		return new UserEntity(email, hashedPassword, TRUE, HA, null, person, roleEntity);
	}

	@Transactional
	private void validateEmailInUse(String email) {
		Optional<UserEntity> existingUser = userRepository.findByEmail(email);
		if (existingUser.isPresent()) {
			throw new RegisterProcessException(String.format("El mail %s se encuentra en uso.", email));
		}
	}

	private void validateDocumentInUse(String documentType, String documentNumber) {
		Optional<UserEntity> existingUser = userRepository.findByDocumentTypeAndDocumentNumber(documentType,
				documentNumber);
		if (existingUser.isPresent()) {
			throw new RegisterProcessException(
					String.format("El documento %s %s, se encuentra en uso", documentType, documentNumber));
		}
	}

	private void sendRegistrationEmail(String email, String firstName, String lastName, String password) {
		Context context = new Context();
		context.setVariable("name", new StringBuilder(firstName).append(" ").append(lastName).toString());
		context.setVariable("password", password);

		emailUtils.sendEmail("facu.bonifacich@gmail.com", "Registro en Phanteon Fitness", context,
				"email-register-template");
	}
}
