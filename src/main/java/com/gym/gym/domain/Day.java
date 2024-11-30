package com.gym.gym.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Day {

	private Long id;

	private String name;
	
	private Integer number;
}
