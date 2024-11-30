package com.gym.gym.service;

import static com.gym.gym.errors.Errors.CUSTOMER_BY_USERID_NOTFOUND_MSG_ERROR;
import static com.gym.gym.errors.Errors.CUSTOMER_NOTFOUND_MSG_ERROR;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.gym.gym.domain.Customer;
import com.gym.gym.domain.PaginatedObject;
import com.gym.gym.domain.enums.UserStatus;
import com.gym.gym.repositories.customer.CustomerEntity;
import com.gym.gym.repositories.customer.CustomerRepository;
import com.gym.gym.service.mapper.CustomerMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CustomerService {

	private final CustomerRepository customerRepository;

	private final CustomerMapper customerMapper;

	public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
		this.customerRepository = customerRepository;
		this.customerMapper = customerMapper;
	}

	public CustomerEntity findById(Long id) {
		return customerRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(CUSTOMER_NOTFOUND_MSG_ERROR.withParams(id)));
	}

	public CustomerEntity findByUserId(Long userId) {
		return customerRepository.findByUserId(userId).orElseThrow(
				() -> new EntityNotFoundException(CUSTOMER_BY_USERID_NOTFOUND_MSG_ERROR.withParams(userId)));
	}

	public PaginatedObject<Customer> findAllCustomers(String documentfilter, String status, Integer pageNumber, Integer size,
			String sortColumn, String sortDirection) {
		UserStatus userStatusFilter = (Objects.isNull(status)) ? null : UserStatus.valueOf(status);
		PageRequest pageRequest = PageRequest.of(pageNumber, size,
				Sort.by(Direction.fromString(sortDirection), resolveSortProperty(sortColumn)));
		Page<CustomerEntity> paginatedCustomerList = customerRepository.findAllPaginatedAndFiltered(documentfilter, userStatusFilter,
				pageRequest);
		return new PaginatedObject<Customer>(customerMapper.entityListToDomainList(paginatedCustomerList.getContent()),
				paginatedCustomerList.getPageable().getPageNumber(), paginatedCustomerList.getNumberOfElements(),
				paginatedCustomerList.getTotalPages(), paginatedCustomerList.getTotalElements());
	}

	private String resolveSortProperty(String property) {
		Map<String, String> sortProperties = new HashMap<String, String>();
		sortProperties.put("email", "user.email");
		sortProperties.put("firstName", "user.person.firstName");
		sortProperties.put("lastName", "user.person.lastName");
		sortProperties.put("documentNumber", "user.person.documetNumber");
		sortProperties.put("expireDate", "user.expireDate");
		sortProperties.put("status", "user.status");
		return sortProperties.get(property);
	}
}
