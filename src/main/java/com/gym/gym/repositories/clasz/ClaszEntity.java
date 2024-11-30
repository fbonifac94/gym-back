package com.gym.gym.repositories.clasz;

import com.gym.gym.domain.enums.Status;
import com.gym.gym.repositories.day.DayEntity;
import com.gym.gym.repositories.schedule.ScheduleEntity;
import com.gym.gym.repositories.teacher.TeacherEntity;

import jakarta.persistence.CascadeType;
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
@Table(name = "class")
@Data
@Entity
public class ClaszEntity {

	@Id
	@Column(name = "class_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "day_id", nullable = false)
	private DayEntity day;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "schedule_id_init", referencedColumnName = "schedule_id", nullable = false)
	private ScheduleEntity initSchedule;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "schedule_id_end", referencedColumnName = "schedule_id", nullable = false)
	private ScheduleEntity endSchedule;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "teacher_id", nullable = false)
	private TeacherEntity teacher;

	@Column(name = "class_name")
	private String name;

	@Column(name = "class_persons_amount")
	private Integer classPersonsAmount;

	@Column(name = "class_status")
	@Enumerated(EnumType.STRING)
	private Status status;

	public ClaszEntity(DayEntity day, ScheduleEntity initSchedule, ScheduleEntity endSchedule, TeacherEntity teacher,
			String name, Integer classPersonsAmount, Status status) {
		this.day = day;
		this.initSchedule = initSchedule;
		this.endSchedule = endSchedule;
		this.teacher = teacher;
		this.name = name;
		this.classPersonsAmount = classPersonsAmount;
		this.status = status;
	}

}
