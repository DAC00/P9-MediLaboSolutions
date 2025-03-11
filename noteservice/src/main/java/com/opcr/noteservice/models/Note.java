package com.opcr.noteservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    @Id
    private Integer id;
    private Integer idPatient;
    private String patientName;
    private String text;
}
