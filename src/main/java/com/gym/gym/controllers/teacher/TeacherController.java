package com.gym.gym.controllers.teacher;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gym.gym.domain.PaginatedObject;
import com.gym.gym.domain.Teacher;
import com.gym.gym.domain.enums.UserStatus;
import com.gym.gym.service.TeacherService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/teachers")
@AllArgsConstructor
public class TeacherController {

	private final TeacherService teacherService;

	@GetMapping
	public ResponseEntity<PaginatedObject<Teacher>> getAllCustomers(
			@RequestParam(required = false, name = "documentfilter") String documentfilter,
			@RequestParam(required = true, name = "pageNumber") Integer pageNumber,
			@RequestParam(required = true, name = "pageSize") Integer pageSize,
			@RequestParam(required = true, name = "sortColumn") String sortColumn,
			@RequestParam(required = true, name = "sortDirection") String sortDirection) {
		PaginatedObject<Teacher> paginatedCustomersList = this.teacherService.findAllTeachers(documentfilter,
				pageNumber, pageSize, sortColumn, sortDirection);
		return ResponseEntity.status(HttpStatus.OK).body(paginatedCustomersList);
	}

	@GetMapping("/status/{status}")
	public ResponseEntity<List<Teacher>> getAllTeachersByStatus(@PathVariable("status") UserStatus status) {
		List<Teacher> teachers = teacherService.findAllTeachersByStatus(status);
		return ResponseEntity.status(HttpStatus.OK).body(teachers);
	}
}
