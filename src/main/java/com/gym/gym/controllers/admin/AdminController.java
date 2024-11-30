package com.gym.gym.controllers.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gym.gym.domain.Admin;
import com.gym.gym.domain.PaginatedObject;
import com.gym.gym.service.AdminService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/admins")
@AllArgsConstructor
public class AdminController {

	private final AdminService adminService;

	@GetMapping()
	public ResponseEntity<PaginatedObject<Admin>> getAllAdmins(
			@RequestParam(required = false, name = "documentfilter") String documentfilter,
			@RequestParam(required = true, name = "pageNumber") Integer pageNumber,
			@RequestParam(required = true, name = "pageSize") Integer pageSize,
			@RequestParam(required = true, name = "sortColumn") String sortColumn,
			@RequestParam(required = true, name = "sortDirection") String sortDirection) {
		PaginatedObject<Admin> paginatedAdminList = this.adminService.findAllAdmins(documentfilter, pageNumber, pageSize, sortColumn,
				sortDirection);
		return ResponseEntity.status(HttpStatus.OK).body(paginatedAdminList);
	}
}
