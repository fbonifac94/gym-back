package com.gym.gym.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaginatedObject <T>{

	private List<T> list;
	
	private Integer page;
	
	private Integer size;
	
	private Integer totalPages;
	
	private Long totalElements;
}
