package com.gym.gym.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.gym.gym.domain.Admin;
import com.gym.gym.repositories.admin.AdminEntity;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface AdminMapper {

	Admin entityToDomain(AdminEntity entity);

	List<Admin> entityListToDomainList(List<AdminEntity> entity);
}
