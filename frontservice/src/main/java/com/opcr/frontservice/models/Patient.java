package com.opcr.frontservice.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    private Integer id;

    @NotBlank(message = "Lastname is required.")
    private String lastName;

    @NotBlank(message = "FirstName is required.")
    private String firstName;

    @Past(message = "Future dates are not allowed.")
    @NotNull(message = "Birthdate is required.")
    private Date birthdate;

    @NotBlank(message = "Gender is required.")
    private String gender;

    private String address;

    private String phoneNumber;
}
