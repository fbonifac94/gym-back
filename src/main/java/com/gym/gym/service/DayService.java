package com.gym.gym.service;

import static org.springframework.data.domain.Sort.Direction.ASC;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gym.gym.domain.Day;
import com.gym.gym.repositories.day.DayEntity;
import com.gym.gym.repositories.day.DayRepository;
import com.gym.gym.service.mapper.DayMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DayService {

	private final DayRepository dayRepository;

	private final DayMapper dayMapper;

	public DayEntity findById(Long dayId) {
		return dayRepository.findById(dayId).orElseThrow(
				() -> new EntityNotFoundException(String.format("No se encontró el día con id %s", dayId)));
	}

	public List<Day> findAllDays() {
		return this.dayMapper.entityListToDomainList(dayRepository.findAll(Sort.by(ASC, "id")));
	}
}
