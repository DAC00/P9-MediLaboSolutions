package com.opcr.patientservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Patient {

    @Id
    private Integer id;

    private String lastName;

    private String firstName;

    private Date birthdate;

    private String gender;

    private String address;

    private String phoneNumber;
}
