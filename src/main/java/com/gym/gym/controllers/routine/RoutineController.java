package com.gym.gym.controllers.routine;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gym.gym.auth.JwtUserData;
import com.gym.gym.domain.PaginatedObject;
import com.gym.gym.domain.Routine;
import com.gym.gym.service.RoutineService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/routines")
public class RoutineController {

	private final RoutineService routineService;

	public RoutineController(RoutineService routineService) {
		this.routineService = routineService;
	}

	@PostMapping
	public ResponseEntity<Void> createRoutine(@AuthenticationPrincipal JwtUserData jwtUserData,
			@Valid @RequestBody CreationRoutineRequest request) {
		this.routineService.createRoutine(request, jwtUserData.getUserId());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateRoutine(@AuthenticationPrincipal JwtUserData jwtUserData,
			@PathVariable("id") Long id, @Valid @RequestBody UpdateRoutineRequest request) {
		this.routineService.updateRoutine(id, request, jwtUserData.getUserId());
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRoutine(@PathVariable("id") Long id) {
		this.routineService.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/routine/{routineId}")
	public ResponseEntity<Routine> getAllRoutinesByCustomerId(@PathVariable("routineId") Long id) {
		Routine routine = this.routineService.findByRoutineId(id);
		return ResponseEntity.status(HttpStatus.OK).body(routine);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<PaginatedObject<Routine>> getAllRoutinesByUserId(@PathVariable("userId") Long id,
			@RequestParam(required = true, name = "pageNumber") Integer pageNumber,
			@RequestParam(required = true, name = "pageSize") Integer pageSize,
			@RequestParam(required = true, name = "sortColumn") String sortColumn,
			@RequestParam(required = true, name = "sortDirection") String sortDirection) {
		PaginatedObject<Routine> routines = this.routineService.findByUserId(id, pageNumber, pageSize, sortColumn,
				sortDirection);
		return ResponseEntity.status(HttpStatus.OK).body(routines);
	}
}
