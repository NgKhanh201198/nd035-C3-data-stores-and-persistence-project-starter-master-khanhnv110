package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.schedule.ScheduleEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "employee")
@Data
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "employee_employee_skill", joinColumns = @JoinColumn(name = "employeeId"), inverseJoinColumns = @JoinColumn(name = "employeeSkillId"))
    private Set<EmployeeSkillEntity> skills = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "employee_day_of_week", joinColumns = @JoinColumn(name = "employeeId"), inverseJoinColumns = @JoinColumn(name = "dayOfWeekId"))
    private Set<DayOfWeekEntity> daysAvailable = new HashSet<>();

    @ManyToMany(mappedBy = "employees")
    private List<ScheduleEntity> schedules;
}
