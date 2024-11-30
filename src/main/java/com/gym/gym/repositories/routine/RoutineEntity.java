package com.gym.gym.repositories.routine;

import static jakarta.persistence.FetchType.LAZY;

import java.time.LocalDate;
import java.util.List;

import com.gym.gym.repositories.customer.CustomerEntity;
import com.gym.gym.repositories.routineexercise.RoutineExerciseEntity;
import com.gym.gym.repositories.teacher.TeacherEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "routine")
@Entity
public class RoutineEntity {

	@Id
	@Column(name = "routine_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "routine_title")
	private String title;

	@Column(name = "routine_creation_date")
	private LocalDate creationDate;

	@Column(name = "routine_modification_date")
	private LocalDate modificationDate;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private CustomerEntity customer;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "teacher_id", nullable = false)
	private TeacherEntity teacher;

	@Column(name = "routine_aditional_info")
	private String aditionalInfo;

	@OneToMany(mappedBy = "routine", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<RoutineExerciseEntity> exercises;

	public RoutineEntity(String title, LocalDate creationDate, LocalDate modificationDate, CustomerEntity customer,
			TeacherEntity teacher, String aditionalInfo) {
		this.title = title;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.customer = customer;
		this.teacher = teacher;
		this.aditionalInfo = aditionalInfo;
	}

}
