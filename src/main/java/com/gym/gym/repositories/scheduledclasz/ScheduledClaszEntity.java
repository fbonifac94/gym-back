package com.gym.gym.repositories.scheduledclasz;

import java.time.LocalDateTime;

import com.gym.gym.domain.enums.Status;
import com.gym.gym.repositories.clasz.ClaszEntity;
import com.gym.gym.repositories.teacher.TeacherEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "scheduled_class")
@Data
@Entity
public class ScheduledClaszEntity {

	@Id
	@Column(name = "scheduled_class_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "scheduled_class_init_date_time")
	private LocalDateTime initDateTime;

	@Column(name = "scheduled_class_end_date_time")
	private LocalDateTime endDateTime;

	@Column(name = "scheduled_class_actual_amount")
	private Integer actualClassPersonsAmount;

	@Column(name = "scheduled_class_total_amount")
	private Integer totalClassPersonsAmount;

	@ManyToOne
	@JoinColumn(name = "class_id", nullable = false)
	private ClaszEntity clasz;

	@ManyToOne
	@JoinColumn(name = "teacher_id", nullable = false)
	private TeacherEntity teacher;

	@Column(name = "scheduled_class_status")
	@Enumerated(EnumType.STRING)
	private Status status;

	public ScheduledClaszEntity(LocalDateTime initDateTime, LocalDateTime endDateTime, Integer actualClassPersonsAmount,
			Integer totalClassPersonsAmount, ClaszEntity clasz, TeacherEntity teacher, Status status) {
		this.initDateTime = initDateTime;
		this.endDateTime = endDateTime;
		this.actualClassPersonsAmount = actualClassPersonsAmount;
		this.totalClassPersonsAmount = totalClassPersonsAmount;
		this.clasz = clasz;
		this.teacher = teacher;
		this.status = status;
	}

}
