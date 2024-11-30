package com.gym.gym.service.mapper;

import org.mapstruct.Mapper;

import com.gym.gym.domain.User;
import com.gym.gym.repositories.user.UserEntity;

@Mapper(componentModel = "spring", uses = PersonMapper.class)
public interface UserMapper {

	User entityToDomain(UserEntity entity);
}
