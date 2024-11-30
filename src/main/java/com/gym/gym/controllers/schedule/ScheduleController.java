package com.gym.gym.controllers.schedule;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gym.gym.domain.Schedule;
import com.gym.gym.service.ScheduleService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/schedules")
@AllArgsConstructor
public class ScheduleController {

	private final ScheduleService scheduleService;
	
	@GetMapping
	public ResponseEntity<List<Schedule>> getAllSchedules() {
		List<Schedule> schedules = scheduleService.findAllSchedules();
		return ResponseEntity.status(HttpStatus.OK).body(schedules);
	}
}
