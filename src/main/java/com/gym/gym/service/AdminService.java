package com.gym.gym.service;

import static com.gym.gym.errors.Errors.ADMIN_NOTFOUND_MSG_ERROR;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.gym.gym.domain.Admin;
import com.gym.gym.domain.PaginatedObject;
import com.gym.gym.repositories.admin.AdminEntity;
import com.gym.gym.repositories.admin.AdminRepository;
import com.gym.gym.service.mapper.AdminMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AdminService {

	private final AdminRepository adminRepository;

	private final AdminMapper adminMapper;

	public AdminService(AdminRepository adminRepository, AdminMapper adminMapper) {
		this.adminRepository = adminRepository;
		this.adminMapper = adminMapper;
	}

	public AdminEntity findById(Long id) {
		return adminRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(ADMIN_NOTFOUND_MSG_ERROR.withParams(id)));
	}

	public PaginatedObject<Admin> findAllAdmins(String documentfilter, Integer pageNumber, Integer pageSize,
			String sortColumn, String sortDirection) {
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize,
				Sort.by(Direction.fromString(sortDirection), resolveSortProperty(sortColumn)));
		Page<AdminEntity> paginatedTeacherList = adminRepository.findAllPaginatedAndFiltered(documentfilter,
				pageRequest);
		return new PaginatedObject<Admin>(adminMapper.entityListToDomainList(paginatedTeacherList.getContent()),
				paginatedTeacherList.getPageable().getPageNumber(), paginatedTeacherList.getNumberOfElements(),
				paginatedTeacherList.getTotalPages(), paginatedTeacherList.getTotalElements());
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
