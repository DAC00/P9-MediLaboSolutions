package com.opcr.patientservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class PatientInfoRisk {
    private Date birthdate;
    private String gender;
}
