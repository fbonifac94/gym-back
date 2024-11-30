package com.gym.gym.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.gym.gym.domain.Customer;
import com.gym.gym.repositories.customer.CustomerEntity;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface CustomerMapper {

	Customer entityToDomain(CustomerEntity entity);

	List<Customer> entityListToDomainList(List<CustomerEntity> entity);
}
