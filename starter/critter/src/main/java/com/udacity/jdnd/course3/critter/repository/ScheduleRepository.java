package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.pet.PetEntity;
import com.udacity.jdnd.course3.critter.schedule.ScheduleEntity;
import com.udacity.jdnd.course3.critter.user.EmployeeEntity;
import com.udacity.jdnd.course3.critter.user.EmployeeSkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    List<ScheduleEntity> findByEmployees(EmployeeEntity employeeEntity);

    List<ScheduleEntity> findByPets(PetEntity petEntity);

    List<ScheduleEntity> findByActivities(EmployeeSkillEntity employeeSkill);
}
