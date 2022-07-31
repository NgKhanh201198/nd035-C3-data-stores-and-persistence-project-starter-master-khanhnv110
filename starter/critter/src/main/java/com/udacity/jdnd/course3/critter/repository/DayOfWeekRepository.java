package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.user.DayOfWeekEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.Optional;

@Repository
public interface DayOfWeekRepository extends JpaRepository<DayOfWeekEntity, Long> {
    Optional<DayOfWeekEntity> findByDayOfWeek(DayOfWeek dayOfWeek);
}
