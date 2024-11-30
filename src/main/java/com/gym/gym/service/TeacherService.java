package com.gym.gym.service;

import static com.gym.gym.errors.Errors.TEACHER_BY_USERID_NOTFOUND_MSG_ERROR;
import static com.gym.gym.errors.Errors.TEACHER_NOTFOUND_MSG_ERROR;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.gym.gym.domain.PaginatedObject;
import com.gym.gym.domain.Teacher;
import com.gym.gym.domain.enums.UserStatus;
import com.gym.gym.repositories.teacher.TeacherEntity;
import com.gym.gym.repositories.teacher.TeacherRepository;
import com.gym.gym.service.mapper.TeacherMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TeacherService {

	private final TeacherRepository teacherRepository;

	private final TeacherMapper teacherMapper;

	public TeacherService(TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
		this.teacherRepository = teacherRepository;
		this.teacherMapper = teacherMapper;
	}

	public TeacherEntity findById(Long id) {
		return teacherRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(TEACHER_NOTFOUND_MSG_ERROR.withParams(id)));
	}

	public TeacherEntity findByUserId(Long userId) {
		return teacherRepository.findByUserId(userId).orElseThrow(
				() -> new EntityNotFoundException(TEACHER_BY_USERID_NOTFOUND_MSG_ERROR.withParams(userId)));
	}

	public PaginatedObject<Teacher> findAllTeachers(String documentFilter, Integer pageNumber, Integer pageSize,
			String sortColumn, String sortDirection) {
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize,
				Sort.by(Direction.fromString(sortDirection), resolveSortProperty(sortColumn)));
		Page<TeacherEntity> paginatedTeacherList = teacherRepository.findAllPaginatedAndFiltered(documentFilter,
				pageRequest);
		return new PaginatedObject<Teacher>(teacherMapper.entityListToDomainList(paginatedTeacherList.getContent()),
				paginatedTeacherList.getPageable().getPageNumber(), paginatedTeacherList.getNumberOfElements(),
				paginatedTeacherList.getTotalPages(), paginatedTeacherList.getTotalElements());
	}

	public List<Teacher> findAllTeachersByStatus(UserStatus status) {
		return teacherMapper.entityListToDomainList(teacherRepository.findAllByStatus(status));
	}

	private String resolveSortProperty(String property) {
		Map<String, String> sortProperties = new HashMap<String, String>();
		sortProperties.put("email", "user.email");
		sortProperties.put("firstName", "user.person.firstName");
		sortProperties.put("lastName", "user.person.lastName");
		sortProperties.put("documentNumber", "user.person.documetNumber");
		sortProperties.put("status", "user.status");
		return sortProperties.get(property);
	}
}
