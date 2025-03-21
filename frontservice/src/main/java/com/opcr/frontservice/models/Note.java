package com.opcr.frontservice.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    private String id;

    private Integer idPatient;

    @NotBlank(message = "A text is required.")
    private String text;
}
