package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.EmployeeSkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkillEntity, Long> {
    Optional<EmployeeSkillEntity> findBySkill(EmployeeSkill employeeSkill);
}
