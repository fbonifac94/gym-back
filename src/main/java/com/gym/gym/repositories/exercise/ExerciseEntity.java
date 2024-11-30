package com.gym.gym.repositories.exercise;

import static jakarta.persistence.FetchType.LAZY;

import com.gym.gym.repositories.exercisetype.ExerciseTypeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "exercise")
@Entity
public class ExerciseEntity {

	@Id
	@Column(name = "exercise_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "exercise_name")
	private String name;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "exercise_type_id")
	private ExerciseTypeEntity exerciseType;

	public ExerciseEntity(String name, ExerciseTypeEntity exerciseType) {
		this.name = name;
		this.exerciseType = exerciseType;
	}

}
