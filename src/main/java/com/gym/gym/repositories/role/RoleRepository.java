package com.gym.gym.repositories.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gym.gym.domain.enums.Role;


@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

	@Query("SELECT r FROM RoleEntity r WHERE r.name = ?1")
	RoleEntity findByName(Role role);
}
