package com.gym.gym.controllers.clasz;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gym.gym.domain.Clasz;
import com.gym.gym.domain.PaginatedObject;
import com.gym.gym.service.ClaszService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/classes")
@AllArgsConstructor
public class ClaszController {

	private final ClaszService claszService;

	@PostMapping
	public ResponseEntity<Void> createClasz(@Valid @RequestBody CreationClaszRequest request) {
		this.claszService.createClass(request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping("/{claszId}")
	public ResponseEntity<Void> updateClasz(@PathVariable("claszId") Long claszId,
			@Valid @RequestBody UpdateClaszRequest request) {
		this.claszService.updateClass(claszId, request);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping
	public ResponseEntity<PaginatedObject<Clasz>> getAllClaszesPaginated(
			@RequestParam(required = true, name = "pageNumber") Integer pageNumber,
			@RequestParam(required = true, name = "pageSize") Integer pageSize,
			@RequestParam(required = true, name = "sortColumn") String sortColumn,
			@RequestParam(required = true, name = "sortDirection") String sortDirection) {
		PaginatedObject<Clasz> paginatedClaszes = claszService.findAllClaszesPaginated(pageNumber, pageSize, sortColumn,
				sortDirection);
		return ResponseEntity.status(HttpStatus.OK).body(paginatedClaszes);
	}

	@PutMapping("/enable/{claszId}")
	public ResponseEntity<Void> enableClasz(@PathVariable("claszId") Long claszId) {
		this.claszService.enableClasz(claszId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PutMapping("/disable/{claszId}")
	public ResponseEntity<Void> disableClasz(@PathVariable("claszId") Long claszId) {
		this.claszService.disableClasz(claszId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
