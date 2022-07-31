package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.PetEntity;
import com.udacity.jdnd.course3.critter.repository.*;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.ScheduleEntity;
import com.udacity.jdnd.course3.critter.user.CustomerEntity;
import com.udacity.jdnd.course3.critter.user.EmployeeEntity;
import com.udacity.jdnd.course3.critter.user.EmployeeSkillEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeSkillRepository employeeSkillRepository;
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, EmployeeRepository employeeRepository, EmployeeSkillRepository employeeSkillRepository, PetRepository petRepository, CustomerRepository customerRepository) {
        this.scheduleRepository = scheduleRepository;
        this.employeeRepository = employeeRepository;
        this.employeeSkillRepository = employeeSkillRepository;
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public ScheduleDTO save(ScheduleDTO scheduleDTO) {
        ScheduleEntity scheduleEntity = dtoToEntity(scheduleDTO);
        return entityToDTO(scheduleRepository.save(scheduleEntity));
    }

    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> scheduleDTOList = scheduleRepository.findAll().stream().map(scheduleEntity -> entityToDTO(scheduleEntity)).collect(Collectors.toList());
        return scheduleDTOList;
    }

    public List<ScheduleDTO> getScheduleForPet(long petId) {
        PetEntity petEntity = petRepository.findById(petId).orElseThrow(() -> new EntityNotFoundException("Not found id: " + petId));
        List<ScheduleEntity> scheduleEntityList = scheduleRepository.findByPets(petEntity);
        List<ScheduleDTO> scheduleDTOList = scheduleEntityList.stream().map(scheduleEntity -> entityToDTO(scheduleEntity)).collect(Collectors.toList());
        return scheduleDTOList;
    }

    public List<ScheduleDTO> getScheduleForEmployee(long employeeId) {
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).orElseThrow(() -> new EntityNotFoundException("Not found id: " + employeeId));
        List<ScheduleEntity> scheduleEntityList = scheduleRepository.findByEmployees(employeeEntity);
        List<ScheduleDTO> scheduleDTOList = scheduleEntityList.stream().map(scheduleEntity -> entityToDTO(scheduleEntity)).collect(Collectors.toList());
        return scheduleDTOList;
    }

    public List<ScheduleDTO> getScheduleForCustomer(long customerId) {
        CustomerEntity customerEntity = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("Not found id: " + customerId));
        List<PetEntity> petEntityList = customerEntity.getPets();
        List<ScheduleEntity> scheduleEntityList = new ArrayList<>();
        for (PetEntity petEntity : petEntityList) {
            List<ScheduleEntity> scheduleEntityLists = scheduleRepository.findByPets(petEntity);
            if (scheduleEntityLists != null) {
                scheduleEntityList.addAll(scheduleEntityLists);
            }
        }
        List<ScheduleDTO> scheduleDTOList = scheduleEntityList.stream().map(scheduleEntity -> entityToDTO(scheduleEntity)).collect(Collectors.toList());
        return scheduleDTOList;
    }

    private ScheduleEntity dtoToEntity(ScheduleDTO scheduleDTO) {
        ScheduleEntity scheduleEntity = new ScheduleEntity();

        scheduleEntity.setDate(scheduleDTO.getDate());

        if (scheduleDTO.getEmployeeIds() != null) {
            List<EmployeeEntity> employeeEntityList = scheduleDTO.getEmployeeIds().stream().map(id -> employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found!"))).collect(Collectors.toList());
            scheduleEntity.setEmployees(employeeEntityList);
        }

        if (scheduleDTO.getPetIds() != null) {
            List<PetEntity> petEntityList = scheduleDTO.getEmployeeIds().stream().map(id -> petRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found!"))).collect(Collectors.toList());
            scheduleEntity.setPets(petEntityList);
        }

        if (scheduleDTO.getActivities() != null) {
            Set<EmployeeSkillEntity> employeeSkillEntityList = scheduleDTO.getActivities().stream().map(employeeSkill -> employeeSkillRepository.findBySkill(employeeSkill).orElseThrow(() -> new EntityNotFoundException("Not found!"))).collect(Collectors.toSet());
            scheduleEntity.setActivities(employeeSkillEntityList);
        }

        return scheduleEntity;
    }

    private ScheduleDTO entityToDTO(ScheduleEntity scheduleEntity) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        scheduleDTO.setId(scheduleDTO.getId());
        scheduleDTO.setDate(scheduleEntity.getDate());

        return scheduleDTO;
    }
}
