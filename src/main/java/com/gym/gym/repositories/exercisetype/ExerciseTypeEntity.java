package com.gym.gym.repositories.exercisetype;

import java.util.List;

import com.gym.gym.repositories.exercise.ExerciseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "exercise_type")
@Entity
public class ExerciseTypeEntity {

	@Id
	@Column(name = "exercise_type_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "exercise_type_name")
	private String name;

	@OneToMany(mappedBy = "exerciseType", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ExerciseEntity> exercises;

	public ExerciseTypeEntity(String name) {
		this.name = name;
	}

}
