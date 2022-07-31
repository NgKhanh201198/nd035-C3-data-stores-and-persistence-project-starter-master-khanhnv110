package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.pet.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<PetEntity, Long> {
    Optional<List<PetEntity>> findByCustomer_Id(long customerId);
}
