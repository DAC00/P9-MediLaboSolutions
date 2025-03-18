package com.opcr.frontservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    private Integer id;
    private String lastName;
    private String firstName;
    private Date birthdate;
    private String gender;
    private String address;
    private String phoneNumber;
}
