package com.gym.gym.service;

import static com.gym.gym.errors.Errors.ROUTINE_NOTFOUND_MSG_ERROR;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.gym.gym.controllers.routine.CreationRoutineRequest;
import com.gym.gym.controllers.routine.UpdateRoutineRequest;
import com.gym.gym.domain.PaginatedObject;
import com.gym.gym.domain.Routine;
import com.gym.gym.repositories.customer.CustomerEntity;
import com.gym.gym.repositories.routine.RoutineEntity;
import com.gym.gym.repositories.routine.RoutineRepository;
import com.gym.gym.repositories.routineexercise.RoutineExerciseEntity;
import com.gym.gym.repositories.teacher.TeacherEntity;
import com.gym.gym.service.mapper.RoutineMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoutineService {

	private final RoutineRepository routineRepository;

	private final RoutineExerciseService routineExerciseService;

	private final CustomerService customerService;

	private final TeacherService teacherService;

	private final RoutineMapper routineMapper;

	@Transactional
	public void createRoutine(CreationRoutineRequest request, Long userId) {
		CustomerEntity customerEntity = customerService.findByUserId(request.getUserId());
		TeacherEntity teacherEntity = teacherService.findByUserId(userId);

		RoutineEntity routineEntity = routineRepository.saveAndFlush(new RoutineEntity(request.getTitle(),
				LocalDate.now(), LocalDate.now(), customerEntity, teacherEntity, request.getAditionalInfo()));

		routineExerciseService.createRoutineExercise(routineEntity, request.getExercises());
	}

	@Transactional
	public void deleteById(Long id) {
		RoutineEntity routineEntity = findById(id);
		routineRepository.delete(routineEntity);
	}

	private RoutineEntity findById(Long id) {
		return routineRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(ROUTINE_NOTFOUND_MSG_ERROR.withParams(id)));
	}

	public PaginatedObject<Routine> findByUserId(Long userId, Integer pageNumber, Integer pageSize, String sortColumn,
			String sortDirection) {
		Pageable pageRequest = PageRequest.of(pageNumber, pageSize,
				Sort.by(Direction.fromString(sortDirection), sortColumn));
		Page<RoutineEntity> paginatedRoutines = routineRepository.findRoutinesByUserId(userId, pageRequest);
		return new PaginatedObject<Routine>(routineMapper.entityListToDomainList(paginatedRoutines.getContent()),
				paginatedRoutines.getPageable().getPageNumber(), paginatedRoutines.getNumberOfElements(),
				paginatedRoutines.getTotalPages(), paginatedRoutines.getTotalElements());
	}

	public Routine findByRoutineId(Long id) {
		RoutineEntity routine = this.findById(id);
		return this.routineMapper.entityToDomain(routine);
	}

	@Transactional
	public void updateRoutine(Long id, UpdateRoutineRequest request, Long userId) {
		RoutineEntity routineEntity = findById(id);
		TeacherEntity teacherEntity = teacherService.findByUserId(userId);
		routineEntity.setTeacher(teacherEntity);
		routineEntity.setAditionalInfo(request.getAditionalInfo());
		routineEntity.setModificationDate(LocalDate.now());
		routineRepository.saveAndFlush(routineEntity);

		routineExerciseService.deleteRoutineExercises(routineEntity.getExercises());
		routineExerciseService.createRoutineExercise(routineEntity, request.getExercises());
	}

	@Transactional
	public void deleteRoutineByExerciseId(Long exerciseId) {
		List<RoutineEntity> routines = this.routineRepository.findAllRoutinesByExerciseId(exerciseId);

		List<RoutineExerciseEntity> routineExerciseListToDelete = routines.stream().map(RoutineEntity::getExercises)
				.flatMap(List::stream).filter(elem -> elem.getExercise().getId().equals(exerciseId)).toList();

		routineExerciseService.deleteRoutineExercises(routineExerciseListToDelete);

		List<Long> routinesIdsToDelete = routines.stream().map(routine_ -> {
			List<RoutineExerciseEntity> routineExercises = routine_.getExercises();
			if (routineExercises.stream()
					.filter(routineExercise -> !routineExercise.getExercise().getId().equals(exerciseId)).toList()
					.size() < 1) {
				return routine_;
			} else {
				return null;
			}
		}).filter(Objects::nonNull).map(RoutineEntity::getId).toList();

		routineRepository.deleteAllRoutinesById(routinesIdsToDelete);
	}
}
