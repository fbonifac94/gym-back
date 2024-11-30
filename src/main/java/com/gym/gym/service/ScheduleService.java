package com.gym.gym.service;

import static org.springframework.data.domain.Sort.Direction.ASC;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gym.gym.domain.Schedule;
import com.gym.gym.repositories.schedule.ScheduleEntity;
import com.gym.gym.repositories.schedule.ScheduleRepository;
import com.gym.gym.service.mapper.ScheduleMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScheduleService {

	private final ScheduleRepository scheduleRepository;

	private final ScheduleMapper scheduleMapper;

	public ScheduleEntity findById(Long scheduleId) {
		return scheduleRepository.findById(scheduleId).orElseThrow(
				() -> new EntityNotFoundException(String.format("No se encontró el horario con id %s", scheduleId)));
	}

	public List<Schedule> findAllSchedules() {
		return scheduleMapper.entityListToDomainList(scheduleRepository.findAll(Sort.by(ASC, "id")));
	}
}
