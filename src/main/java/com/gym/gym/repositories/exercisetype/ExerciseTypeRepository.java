package com.gym.gym.repositories.exercisetype;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseTypeRepository extends JpaRepository<ExerciseTypeEntity, Long> {

}
