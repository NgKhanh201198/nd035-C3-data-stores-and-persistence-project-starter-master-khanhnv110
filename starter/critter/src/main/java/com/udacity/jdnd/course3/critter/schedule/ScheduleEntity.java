package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.PetEntity;
import com.udacity.jdnd.course3.critter.user.EmployeeEntity;
import com.udacity.jdnd.course3.critter.user.EmployeeSkillEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "schedule")
@Data
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "schedule_employee", joinColumns = {@JoinColumn(name = "scheduleId")}, inverseJoinColumns = {@JoinColumn(name = "employeeId")})
    private List<EmployeeEntity> employees = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "schedule_pet", joinColumns = {@JoinColumn(name = "scheduleId")}, inverseJoinColumns = {@JoinColumn(name = "petId")})
    private List<PetEntity> pets = new ArrayList<>();

    private LocalDate date;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "schedule_employee_skill", joinColumns = @JoinColumn(name = "scheduleId"), inverseJoinColumns = @JoinColumn(name = "employeeSkillId"))
    private Set<EmployeeSkillEntity> activities = new HashSet<>();
}
