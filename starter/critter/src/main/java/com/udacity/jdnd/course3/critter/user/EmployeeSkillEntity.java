package com.udacity.jdnd.course3.critter.user;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "employee_skill")
@Data
public class EmployeeSkillEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EmployeeSkill skill;
}
