package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.repository.DayOfWeekRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeSkillRepository;
import com.udacity.jdnd.course3.critter.user.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeSkillRepository employeeSkillRepository;
    private final DayOfWeekRepository dayOfWeekRepository;


    public EmployeeService(EmployeeRepository employeeRepository, EmployeeSkillRepository employeeSkillRepository, DayOfWeekRepository dayOfWeekRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeSkillRepository = employeeSkillRepository;
        this.dayOfWeekRepository = dayOfWeekRepository;
    }

    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = dtoToEntity(employeeDTO);
        return entityToDTO(employeeRepository.save(employeeEntity));
    }

    public EmployeeDTO getById(long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found: " + id));
        return entityToDTO(employeeEntity);
    }

    public EmployeeDTO setAvailability(long id, Set<DayOfWeek> daysAvailable) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found: " + id));
        if (daysAvailable != null) {
            Set<DayOfWeekEntity> dayOfWeekEntitySet = daysAvailable.stream().map(dayOfWeek -> dayOfWeekRepository.findByDayOfWeek(dayOfWeek).orElseThrow(() -> new EntityNotFoundException("Not found!"))).collect(Collectors.toSet());
            employeeEntity.setDaysAvailable(dayOfWeekEntitySet);
        }
        return entityToDTO(employeeRepository.save(employeeEntity));
    }

    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO) {
        Set<EmployeeEntity> employeeEntityList = new HashSet<>();

        DayOfWeekEntity dayOfWeekEntity = dayOfWeekRepository.findByDayOfWeek(employeeRequestDTO.getDate().getDayOfWeek()).orElseThrow(() -> new EntityNotFoundException("Not found!"));

        Set<EmployeeSkillEntity> employeeSkillEntitySet = new HashSet<>();
        for (EmployeeSkill employeeSkill : employeeRequestDTO.getSkills()) {
            EmployeeSkillEntity employeeSkillEntity = employeeSkillRepository.findBySkill(employeeSkill).orElseThrow(() -> new EntityNotFoundException("can't find the Skill"));
            employeeSkillEntitySet.add(employeeSkillEntity);
        }

        for (EmployeeSkillEntity employeeSkillEntity : employeeSkillEntitySet) {
            employeeEntityList.add(employeeRepository.findBySkillsAndDaysAvailable(employeeSkillEntity, dayOfWeekEntity));
        }

        return employeeEntityList.stream().map(employeeEntity -> entityToDTO(employeeEntity)).collect(Collectors.toList());
    }

    private EmployeeEntity dtoToEntity(EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = new EmployeeEntity();

        employeeEntity.setName(employeeDTO.getName());
        if (employeeDTO.getSkills() != null) {
            Set<EmployeeSkillEntity> employeeSkillEntitySet = employeeDTO.getSkills().stream().map(employeeSkill -> employeeSkillRepository.findBySkill(employeeSkill).orElseThrow(() -> new EntityNotFoundException("Not found!"))).collect(Collectors.toSet());
            employeeEntity.setSkills(employeeSkillEntitySet);
        }

        if (employeeDTO.getDaysAvailable() != null) {
            Set<DayOfWeekEntity> dayOfWeekEntitySet = employeeDTO.getDaysAvailable().stream().map(dayOfWeek -> dayOfWeekRepository.findByDayOfWeek(dayOfWeek).orElseThrow(() -> new EntityNotFoundException("Not found!"))).collect(Collectors.toSet());
            employeeEntity.setDaysAvailable(dayOfWeekEntitySet);
        }

        return employeeEntity;
    }

    private EmployeeDTO entityToDTO(EmployeeEntity employeeEntity) {
        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setId(employeeEntity.getId());
        employeeDTO.setName(employeeEntity.getName());
        employeeDTO.setSkills(employeeEntity.getSkills().stream().map(employeeSkillEntity -> employeeSkillEntity.getSkill()).collect(Collectors.toSet()));
        employeeDTO.setDaysAvailable(employeeEntity.getDaysAvailable().stream().map(dayOfWeekEntity -> dayOfWeekEntity.getDayOfWeek()).collect(Collectors.toSet()));

        return employeeDTO;
    }
}
