package com.gym.gym.repositories.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gym.gym.domain.enums.UserStatus;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	public Optional<UserEntity> findByEmail(String email);

	@Query("SELECT u FROM UserEntity u WHERE u.person.documentType = :documentType AND u.person.documetNumber = :documentNumber")
	public Optional<UserEntity> findByDocumentTypeAndDocumentNumber(@Param("documentType") String documentType,
			@Param("documentNumber") String documentNumber);

	public Optional<UserEntity> findByEmailAndPassword(String email, String oldPasswordEncoded);

	@Query("SELECT u FROM UserEntity u JOIN FETCH u.role WHERE u.status = :status")
	public List<UserEntity> findByStatus(@Param("status") UserStatus status);
}
