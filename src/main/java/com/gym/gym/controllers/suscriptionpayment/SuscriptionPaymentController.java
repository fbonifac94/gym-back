package com.gym.gym.controllers.suscriptionpayment;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gym.gym.domain.PaginatedObject;
import com.gym.gym.domain.SuscriptionPayment;
import com.gym.gym.service.SuscriptionPaymentService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/suscriptions")
@AllArgsConstructor
public class SuscriptionPaymentController {

	private final SuscriptionPaymentService suscriptionPaymentService;

	@GetMapping("/user/{userId}")
	public ResponseEntity<PaginatedObject<SuscriptionPayment>> getSuscriptionPaymentsByUserId(
			@PathVariable("userId") Long userId, @RequestParam(required = true, name = "pageNumber") Integer pageNumber,
			@RequestParam(required = true, name = "pageSize") Integer pageSize) {
		PaginatedObject<SuscriptionPayment> paginatedSuscriptionPayments = this.suscriptionPaymentService
				.getPaymentsFromUser(pageNumber, pageSize, userId);
		return ResponseEntity.status(HttpStatus.OK).body(paginatedSuscriptionPayments);
	}

	@GetMapping
	public ResponseEntity<PaginatedObject<SuscriptionPayment>> getSuscriptionPayments(
			@RequestParam(required = false, name = "documetNumber") String documentNumber,
			@RequestParam(required = false, name = "startDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
			@RequestParam(required = false, name = "endDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate,
			@RequestParam(required = true, name = "pageNumber") Integer pageNumber,
			@RequestParam(required = true, name = "pageSize") Integer pageSize) {
		PaginatedObject<SuscriptionPayment> paginatedSuscriptionPayments = this.suscriptionPaymentService
				.getSuscriptionPayments(documentNumber, startDate, endDate, pageNumber, pageSize);
		return ResponseEntity.status(HttpStatus.OK).body(paginatedSuscriptionPayments);
	}

	@DeleteMapping("/suscription/{suscriptionPaymentId}")
	public ResponseEntity<Void> deleteSuscriptionPayment(
			@PathVariable("suscriptionPaymentId") Long suscriptionPaymentId) {
		this.suscriptionPaymentService.deletePayment(suscriptionPaymentId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/suscription/user/{userId}")
	public ResponseEntity<PaginatedObject<SuscriptionPayment>> postPayment(@PathVariable("userId") Long userId,
			@RequestBody PostSuscriptionRequest request) {
		this.suscriptionPaymentService.postPayment(userId, request);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
