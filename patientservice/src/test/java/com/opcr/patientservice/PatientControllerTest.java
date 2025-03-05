package com.opcr.patientservice;


import com.opcr.patientservice.models.Patient;
import com.opcr.patientservice.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource("/test.properties")
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void getPatientsTest() throws Exception {
        this.mockMvc.perform(get("/patient/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lastName").value("One"))
                .andExpect(jsonPath("$[1].lastName").value("Two"))
                .andExpect(jsonPath("$[2].lastName").value("Three"))
                .andExpect(jsonPath("$[3].lastName").value("Four")
                );
    }

    @Test
    public void getPatientFourTest() throws Exception {
        this.mockMvc.perform(get("/patient/4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".id").value(4))
                .andExpect(jsonPath(".lastName").value("Four"))
                .andExpect(jsonPath(".firstName").value("Test"))
                .andExpect(jsonPath(".birthdate").value("2002-06-02"))
                .andExpect(jsonPath(".gender").value("F"))
                .andExpect(jsonPath(".address").value("4 Valley Dr"))
                .andExpect(jsonPath(".phoneNumber").value("400-555-6666")
                );
    }

    @Test
    public void updatePatientOneTest() throws Exception {
        String content = "{\"id\":1," +
                "\"lastName\":\"Updated\"," +
                "\"firstName\":\"Test\"," +
                "\"birthdate\":\"1966-12-03\"," +
                "\"gender\":\"F\"," +
                "\"address\":\"1 Brookside St\"," +
                "\"phoneNumber\":\"555-666-7777\"}";

        this.mockMvc.perform(put("/patient/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        );

        Patient patient = patientRepository.findById(1).orElse(null);

        assertNotNull(patient);
        assertEquals("Updated", patient.getLastName());
        assertEquals("555-666-7777", patient.getPhoneNumber());
        assertEquals("1 Brookside St", patient.getAddress());
    }

    @Test
    public void addPatientTest() throws Exception {
        String content = "{\"lastName\":\"New\"," +
                "\"firstName\":\"Test\"," +
                "\"birthdate\":\"1999-10-13\"," +
                "\"gender\":\"F\"," +
                "\"address\":\"New St\"," +
                "\"phoneNumber\":\"444-222-7777\"}";

        this.mockMvc.perform(post("/patient/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated());

        List<Patient> patients = patientRepository.findAll();
        Patient newPatient = patients.getLast();

        assertEquals(5, patients.size());
        assertEquals("New", newPatient.getLastName());
        assertEquals("Test", newPatient.getFirstName());
        assertEquals("1999-10-13", newPatient.getBirthdate().toString());
        assertEquals("F", newPatient.getGender());
        assertEquals("New St", newPatient.getAddress());
        assertEquals("444-222-7777", newPatient.getPhoneNumber());
    }
}
