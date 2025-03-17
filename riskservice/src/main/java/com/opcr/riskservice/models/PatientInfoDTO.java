package com.opcr.riskservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientInfoDTO {
    private String gender;
    private Date birthdate;
}
