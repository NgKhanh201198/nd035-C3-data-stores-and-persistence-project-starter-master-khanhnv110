package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetEntity;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PetService {
    private final PetRepository petRepository;
    private final CustomerRepository customerService;

    public PetService(PetRepository petRepository, CustomerRepository customerService) {
        this.petRepository = petRepository;
        this.customerService = customerService;
    }

    public PetDTO save(PetDTO petDTO) {
        PetEntity petEntity = dtoToEntity(petDTO);
        return entityToDTO(petRepository.save(petEntity));
    }

    public PetDTO getPetById(long id) {
        return entityToDTO(petRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found id: " + id)));
    }

    public List<PetDTO> getAllPets() {
        List<PetDTO> petDTOList = petRepository.findAll().stream().map(petEntity -> entityToDTO(petEntity)).collect(Collectors.toList());
        return petDTOList;
    }

    public List<PetDTO> getPetsByOwner(long customerId) {
        List<PetDTO> petDTOList = petRepository.findByCustomer_Id(customerId).orElseThrow(() -> new EntityNotFoundException("Not found id: " + customerId)).stream().map(petEntity -> entityToDTO(petEntity)).collect(Collectors.toList());
        return petDTOList;
    }

    private PetEntity dtoToEntity(PetDTO petDTO) {
        PetEntity petEntity = new PetEntity();
        petEntity.setType(petDTO.getType());
        petEntity.setName(petDTO.getName());
        petEntity.setCustomer(customerService.findById(petDTO.getOwnerId()).orElseThrow(() -> new EntityNotFoundException("Not found id: " + petDTO.getOwnerId())));
        petEntity.setBirthDate(petDTO.getBirthDate());
        petEntity.setNotes(petDTO.getNotes());
        return petEntity;
    }

    private PetDTO entityToDTO(PetEntity petEntity) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(petEntity.getId());
        petDTO.setType(petEntity.getType());
        petDTO.setName(petEntity.getName());
        petDTO.setOwnerId(petEntity.getCustomer().getId());
        petDTO.setBirthDate(petEntity.getBirthDate());
        petDTO.setNotes(petEntity.getNotes());
        return petDTO;
    }
}
