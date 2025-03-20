package com.opcr.frontservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Risk {
    private Integer idPatient;
    private String riskStatus;
}