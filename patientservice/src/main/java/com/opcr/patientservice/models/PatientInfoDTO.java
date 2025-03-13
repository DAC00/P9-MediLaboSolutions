package com.opcr.patientservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class PatientInfoDTO {
    private Date birthdate;
    private String gender;
}
