package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.PetEntity;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.CustomerEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;

    public CustomerService(CustomerRepository customerRepository, PetRepository petRepository) {
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    public CustomerDTO save(CustomerDTO customerDTO) {
        CustomerEntity customerEntity = dtoToEntity(customerDTO);
        return entityToDTO(customerRepository.save(customerEntity));
    }

    public CustomerEntity getCustomerById(long id) {
        return customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found id: " + id));
    }

    public List<CustomerDTO> getAllCustomers() {
        List<CustomerEntity> customerEntityList = customerRepository.findAll();
        return customerEntityList.stream().map(customerEntity -> entityToDTO(customerEntity)).collect(Collectors.toList());
    }

    public CustomerDTO getCustomerByPetsId(long petId) {
        PetEntity petEntity = petRepository.findById(petId).orElseThrow(() -> new EntityNotFoundException("Not found id: " + petId));
        return entityToDTO(petEntity.getCustomer());
    }

    private CustomerEntity dtoToEntity(CustomerDTO customerDTO) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName(customerDTO.getName());
        customerEntity.setPhoneNumber(customerDTO.getPhoneNumber());
        customerEntity.setNotes(customerDTO.getNotes());
        return customerEntity;
    }

    private CustomerDTO entityToDTO(CustomerEntity customerEntity) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customerEntity.getId());
        customerDTO.setName(customerEntity.getName());
        customerDTO.setPhoneNumber(customerEntity.getPhoneNumber());
        customerDTO.setNotes(customerEntity.getNotes());
        if (customerEntity.getPets() != null) {
            List<Long> longList = customerEntity.getPets().stream().map(petEntity -> petEntity.getId()).collect(Collectors.toList());
            customerDTO.setPetIds(longList);
        }
        return customerDTO;
    }
}
