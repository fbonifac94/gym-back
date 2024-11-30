package com.gym.gym.controllers.clasz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreationClaszRequest {

	private Long teacherId;
	
	private String name;

	private Long dayId;

	private Long initScheduleId;

	private Long endScheduleId;

	private Integer amountPerClass;
}
