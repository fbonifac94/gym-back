package com.gym.gym.service;

import static com.gym.gym.domain.enums.Status.BA;
import static com.gym.gym.domain.enums.Status.HA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.gym.gym.controllers.clasz.CreationClaszRequest;
import com.gym.gym.controllers.clasz.UpdateClaszRequest;
import com.gym.gym.domain.Clasz;
import com.gym.gym.domain.PaginatedObject;
import com.gym.gym.domain.enums.Status;
import com.gym.gym.exception.ClaszInConflictException;
import com.gym.gym.repositories.clasz.ClaszEntity;
import com.gym.gym.repositories.clasz.ClaszRepository;
import com.gym.gym.repositories.day.DayEntity;
import com.gym.gym.repositories.schedule.ScheduleEntity;
import com.gym.gym.repositories.teacher.TeacherEntity;
import com.gym.gym.service.mapper.ClaszMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClaszService {

	private final ClaszRepository claszRepository;

	private final ClaszMapper claszMapper;

	private final DayService dayService;

	private final ScheduleService scheduleService;

	private final TeacherService teacherService;

	public void createClass(CreationClaszRequest request) {
		TeacherEntity teacher = teacherService.findById(request.getTeacherId());

		DayEntity day = dayService.findById(request.getDayId());

		ScheduleEntity initSchedule = scheduleService.findById(request.getInitScheduleId());

		ScheduleEntity endSchedule = scheduleService.findById(request.getEndScheduleId());

		findClassesInConflict(null, teacher, day, initSchedule, endSchedule);

		ClaszEntity newClasz = new ClaszEntity(day, initSchedule, endSchedule, teacher, request.getName(),
				request.getAmountPerClass(), HA);

		claszRepository.saveAndFlush(newClasz);
	}

	private void findClassesInConflict(Long claszId, TeacherEntity teacher, DayEntity day, ScheduleEntity initSchedule,
			ScheduleEntity endSchedule) {
		List<ClaszEntity> clasesConflicto = claszRepository.findConflictingClasses(teacher.getId(), day.getId(),
				initSchedule.getTime(), endSchedule.getTime());

		if (Objects.nonNull(claszId)) {
			clasesConflicto = clasesConflicto.stream().filter(elem -> !elem.getId().equals(claszId)).toList();
		}

		if (!clasesConflicto.isEmpty()) {
			throw new ClaszInConflictException("El profesor ya tiene una clase en el horario solicitado");
		}
	}

	public ClaszEntity findById(Long claszId) {
		return claszRepository.findById(claszId).orElseThrow(
				() -> new EntityNotFoundException(String.format("No se encontró la clase con id %s", claszId)));
	}

	public List<ClaszEntity> findAll() {
		return claszRepository.findAll();
	}

	public List<ClaszEntity> findAllClaszesByStatus(Status status) {
		return claszRepository.findAllByStatus(status);
	}

	public List<ClaszEntity> findAllAvailableClaszes() {
		return claszRepository.findAllAvailableClaszes();
	}

	public PaginatedObject<Clasz> findAllClaszesPaginated(Integer pageNumber, Integer pageSize, String sortColumn,
			String sortDirection) {
		Pageable pageRequest = PageRequest.of(pageNumber, pageSize,
				Sort.by(Direction.fromString(sortDirection), resolveSortProperty(sortColumn)));
		Page<ClaszEntity> paginatedClaszes = claszRepository.findAll(pageRequest);
		return new PaginatedObject<Clasz>(claszMapper.entityListToDomainList(paginatedClaszes.getContent()),
				paginatedClaszes.getPageable().getPageNumber(), paginatedClaszes.getNumberOfElements(),
				paginatedClaszes.getTotalPages(), paginatedClaszes.getTotalElements());
	}

	private String resolveSortProperty(String property) {
		Map<String, String> sortProperties = new HashMap<String, String>();
		sortProperties.put("name", "name");
		sortProperties.put("day", "day.number");
		sortProperties.put("initSchedule", "initSchedule.time");
		sortProperties.put("endSchedule", "endSchedule.time");
		sortProperties.put("teacher", "teacher.user.person.lastName");
		sortProperties.put("amount", "classPersonsAmount");
		sortProperties.put("status", "status");
		return sortProperties.get(property);
	}

	public void updateClass(Long claszId, @Valid UpdateClaszRequest request) {
		ClaszEntity clasz = claszRepository.findById(claszId).orElseThrow(
				() -> new EntityNotFoundException(String.format("No se encontró la clase con id %s", claszId)));

		TeacherEntity teacher = teacherService.findById(request.getTeacherId());

		DayEntity day = dayService.findById(request.getDayId());

		ScheduleEntity initSchedule = scheduleService.findById(request.getInitScheduleId());

		ScheduleEntity endSchedule = scheduleService.findById(request.getEndScheduleId());

		findClassesInConflict(claszId, teacher, day, initSchedule, endSchedule);

		clasz.setTeacher(teacher);
		clasz.setDay(day);
		clasz.setInitSchedule(initSchedule);
		clasz.setEndSchedule(endSchedule);
		clasz.setClassPersonsAmount(request.getAmountPerClass());

		claszRepository.saveAndFlush(clasz);
	}

	public void enableClasz(Long claszId) {
		changeClaszStatus(claszId, HA);
	}

	public void disableClasz(Long claszId) {
		changeClaszStatus(claszId, BA);
	}

	private void changeClaszStatus(Long claszId, Status status) {
		ClaszEntity clasz = findById(claszId);
		clasz.setStatus(status);
		claszRepository.saveAndFlush(clasz);
	}

}
