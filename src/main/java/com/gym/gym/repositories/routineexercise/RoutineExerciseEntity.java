package com.gym.gym.repositories.routineexercise;

import static jakarta.persistence.FetchType.LAZY;

import com.gym.gym.repositories.exercise.ExerciseEntity;
import com.gym.gym.repositories.routine.RoutineEntity;

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
@Table(name = "routine_exercise")
@Entity
public class RoutineExerciseEntity {

	@Id
	@Column(name = "routine_exercise_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "routine_id", nullable = false)
	private RoutineEntity routine;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "exercise_id", nullable = false)
	private ExerciseEntity exercise;

	@Column(name = "routine_exercise_repetitions")
	private Integer repetitions;

	@Column(name = "routine_exercise_series")
	private Integer series;

	@Column(name = "routine_exercise_aditional_info")
	private String aditionalInfo;

	public RoutineExerciseEntity(RoutineEntity routine, ExerciseEntity exercise, Integer repetitions, Integer series,
			String aditionalInfo) {
		this.routine = routine;
		this.exercise = exercise;
		this.repetitions = repetitions;
		this.series = series;
		this.aditionalInfo = aditionalInfo;
	}

}
