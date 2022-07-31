package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.user.DayOfWeekEntity;
import com.udacity.jdnd.course3.critter.user.EmployeeEntity;
import com.udacity.jdnd.course3.critter.user.EmployeeSkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    EmployeeEntity findBySkillsAndDaysAvailable(EmployeeSkillEntity employeeSkill, DayOfWeekEntity dayOfWeekEntity);
}
