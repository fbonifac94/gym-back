package com.gym.gym.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.gym.gym.domain.SuscriptionPayment;
import com.gym.gym.repositories.suscriptionpayment.SuscriptionPaymentEntity;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface SuscriptionPaymentMapper {

	SuscriptionPayment entityToDomain(SuscriptionPaymentEntity entity);

	List<SuscriptionPayment> entityListToDomainList(List<SuscriptionPaymentEntity> entity);
}
