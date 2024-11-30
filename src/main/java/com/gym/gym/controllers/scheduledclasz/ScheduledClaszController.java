package com.gym.gym.controllers.scheduledclasz;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gym.gym.auth.JwtUserData;
import com.gym.gym.domain.PaginatedObject;
import com.gym.gym.domain.ScheduledClasz;
import com.gym.gym.service.ScheduledClaszService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/classes/scheduled")
@AllArgsConstructor
public class ScheduledClaszController {

	private final ScheduledClaszService scheduledClaszService;

	@GetMapping
	public ResponseEntity<PaginatedObject<ScheduledClasz>> getAllScheduledClasz(
			@AuthenticationPrincipal JwtUserData jwtUserData,
			@RequestParam(required = true, name = "pageNumber") Integer pageNumber,
			@RequestParam(required = true, name = "pageSize") Integer pageSize,
			@RequestParam(required = true, name = "sortColumn") String sortColumn,
			@RequestParam(required = true, name = "sortDirection") String sortDirection) {
		PaginatedObject<ScheduledClasz> paginatedAdminList = this.scheduledClaszService.findAllScheduledClasz(
				jwtUserData.getUserId(), jwtUserData.getRole(), pageNumber, pageSize, sortColumn, sortDirection);
		return ResponseEntity.status(HttpStatus.OK).body(paginatedAdminList);
	}

	@PostMapping("/cancel/{scheduledClaszId}")
	public ResponseEntity<PaginatedObject<ScheduledClasz>> cancelScheduledClasz(
			@PathVariable("scheduledClaszId") Long scheduledClaszId) {
		this.scheduledClaszService.cancelScheduledClasz(scheduledClaszId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
