package com.gym.gym.controllers.scheduledclaszinscription;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gym.gym.auth.JwtUserData;
import com.gym.gym.domain.ScheduledClaszInscription;
import com.gym.gym.service.ScheduledClaszInscriptionService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/classes/scheduled/inscriptions")
@AllArgsConstructor
public class ScheduledClaszInscriptionController {

	private final ScheduledClaszInscriptionService scheduledClaszInscriptionService;

	@PostMapping("/enroll/{scheduledClassId}")
	public ResponseEntity<Void> enrollToScheduledClasz(@AuthenticationPrincipal JwtUserData jwtUserData,
			@PathVariable("scheduledClassId") Long scheduledClassId) {
		scheduledClaszInscriptionService.enrollToClass(jwtUserData.getUserId(), scheduledClassId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/erase/{scheduledClassId}")
	public ResponseEntity<Void> eraseToScheduledClasz(@AuthenticationPrincipal JwtUserData jwtUserData,
			@PathVariable("scheduledClassId") Long scheduledClassId) {
		scheduledClaszInscriptionService.eraseClassInscription(jwtUserData.getUserId(), scheduledClassId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/{scheduledClassId}")
	public ResponseEntity<List<ScheduledClaszInscription>> getSheduledClaszesInscriptionByScheduledClassId(
			@PathVariable("scheduledClassId") Long scheduledClassId) {
		List<ScheduledClaszInscription> inscriptions = scheduledClaszInscriptionService
				.getSheduledClaszesInscriptionByScheduledClassId(scheduledClassId);
		return ResponseEntity.ok(inscriptions);
	}

	@GetMapping("/customer/{userId}")
	public ResponseEntity<List<ScheduledClaszInscription>> getSheduledClaszesInscriptionByCustomerId(
			@PathVariable("userId") Long userId) {
		List<ScheduledClaszInscription> inscriptions = scheduledClaszInscriptionService
				.getSheduledClaszesCustotmerInscriptionByUserId(userId);
		return ResponseEntity.ok(inscriptions);
	}
}
