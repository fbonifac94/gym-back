package com.gym.gym.repositories.passwordrecuperation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRecuperationRepository extends JpaRepository<PasswordRecuperationEntity, Long> {

	@Modifying
	@Query(value = "DELETE FROM PasswordRecuperationEntity pr WHERE pr.email = :email")
	void deletePasswordsRecuperationCodesByEmail(@Param("email") String email);

	@Query(value = "SELECT pr FROM PasswordRecuperationEntity pr WHERE pr.email = :email ORDER BY pr.creationDateTime DESC")
	List<PasswordRecuperationEntity> findByEmail(@Param("email") String email);
}
