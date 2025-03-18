package com.opcr.frontservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    private Integer id;
    private Integer idPatient;
    private String text;
}
