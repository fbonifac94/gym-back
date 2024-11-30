package com.gym.gym.controllers.day;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gym.gym.domain.Day;
import com.gym.gym.service.DayService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/days")
@AllArgsConstructor
public class DayController {

	private final DayService dayService;

	@GetMapping
	public ResponseEntity<List<Day>> getAllDays() {
		List<Day> days = dayService.findAllDays();
		return ResponseEntity.status(HttpStatus.OK).body(days);
	}
}
