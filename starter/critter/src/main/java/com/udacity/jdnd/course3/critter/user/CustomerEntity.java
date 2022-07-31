package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.PetEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "customer")
@Data
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String phoneNumber;

    private String notes;

    @OneToMany(targetEntity = PetEntity.class, mappedBy = "customer", cascade = CascadeType.ALL)
    private List<PetEntity> pets;
}
