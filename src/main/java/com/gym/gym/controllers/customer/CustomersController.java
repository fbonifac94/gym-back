package com.gym.gym.controllers.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gym.gym.domain.Customer;
import com.gym.gym.domain.PaginatedObject;
import com.gym.gym.service.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomersController {

	private final CustomerService customerService;

	public CustomersController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping()
	public ResponseEntity<PaginatedObject<Customer>> getAllCustomers(
			@RequestParam(required = false, name = "documentfilter") String documentfilter,
			@RequestParam(required = false, name = "status") String status,
			@RequestParam(required = true, name = "pageNumber") Integer pageNumber,
			@RequestParam(required = true, name = "pageSize") Integer pageSize,
			@RequestParam(required = true, name = "sortColumn") String sortColumn,
			@RequestParam(required = true, name = "sortDirection") String sortDirection) {
		PaginatedObject<Customer> paginatedCustomersList = this.customerService.findAllCustomers(documentfilter, status, pageNumber,
				pageSize, sortColumn, sortDirection);
		return ResponseEntity.status(HttpStatus.OK).body(paginatedCustomersList);
	}
}
