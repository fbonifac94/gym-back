package com.gym.gym.domain;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Schedule {

	private Long id;

	private LocalTime time;
}
