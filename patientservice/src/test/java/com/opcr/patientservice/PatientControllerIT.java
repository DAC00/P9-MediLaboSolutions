package com.opcr.patientservice;


import com.opcr.patientservice.models.Patient;
import com.opcr.patientservice.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@TestPropertySource("/test.properties")
@AutoConfigureWebTestClient
public class PatientControllerIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void getPatientsTest() throws Exception {
        webTestClient.get()
                .uri("/api/patient/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBodyList(Patient.class)
                .consumeWith(response -> {
                    List<Patient> patients = response.getResponseBody();
                    assertNotNull(patients);
                    assertEquals(4, patients.size());
                });
    }

    @Test
    public void getPatientOkTest() throws Exception {
        webTestClient.get()
                .uri("/api/patient/4")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(Patient.class)
                .consumeWith(response -> {
                    Patient patient = response.getResponseBody();
                    assertNotNull(patient);
                    assertEquals(4, patient.getId());
                    assertEquals("Four", patient.getLastName());
                    assertEquals("Test", patient.getFirstName());
                    assertEquals("2002-06-02", patient.getBirthdate().toString());
                    assertEquals("F", patient.getGender());
                    assertEquals("4 Valley Dr", patient.getAddress());
                    assertEquals("400-555-6666", patient.getPhoneNumber());
                });
    }

    @Test
    public void getPatientNotFundTest() throws Exception {
        int idPatientWhoDoesNotExist = 100;
        webTestClient.get()
                .uri("/api/patient/" + idPatientWhoDoesNotExist)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody(Patient.class)
                .consumeWith(response -> {
                    Patient patient = response.getResponseBody();
                    assertNull(patient);
                    ;
                });
    }

    @Test
    public void updatePatientOkTest() throws Exception {
        int idPatient = 2;
        Patient patientToSave = new Patient();
        patientToSave.setId(idPatient);
        patientToSave.setLastName("Two");
        patientToSave.setFirstName("Test");
        patientToSave.setBirthdate(Date.valueOf("1945-06-02"));
        patientToSave.setGender("M");
        patientToSave.setAddress("test test");
        patientToSave.setPhoneNumber("111-222-5555");

        webTestClient.put()
                .uri("/api/patient/update/" + idPatient)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(patientToSave))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(Patient.class)
                .consumeWith(response -> {
                    Patient patientSaved = response.getResponseBody();
                    assertNotNull(patientSaved);
                    assertEquals(patientToSave.getId(), patientSaved.getId());
                    assertEquals(patientToSave.getLastName(), patientSaved.getLastName());
                    assertEquals(patientToSave.getFirstName(), patientSaved.getFirstName());
                    assertEquals(patientToSave.getGender(), patientSaved.getGender());
                    assertEquals(patientToSave.getBirthdate().toString(), patientSaved.getBirthdate().toString());
                    assertEquals(patientToSave.getAddress(), patientSaved.getAddress());
                    assertEquals(patientToSave.getPhoneNumber(), patientSaved.getPhoneNumber());
                });
    }

    @Test
    public void updatePatientNotFoundTest() throws Exception {
        int idPatientWhoDoesNotExist = 100;
        Patient patientToSave = new Patient();
        patientToSave.setId(idPatientWhoDoesNotExist);
        patientToSave.setLastName("Two");
        patientToSave.setFirstName("Test");
        patientToSave.setBirthdate(Date.valueOf("1945-06-02"));
        patientToSave.setGender("M");

        webTestClient.put()
                .uri("/api/patient/update/" + idPatientWhoDoesNotExist)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(patientToSave))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody(Patient.class)
                .consumeWith(response -> {
                    Patient patient = response.getResponseBody();
                    assertNull(patient);
                });
    }

    @Test
    public void addPatientTest() throws Exception {
        Patient patientToSave = new Patient();
        patientToSave.setLastName("Test");
        patientToSave.setFirstName("BOB");
        patientToSave.setBirthdate(Date.valueOf("1966-12-03"));
        patientToSave.setGender("M");
        patientToSave.setAddress("test Street");
        patientToSave.setPhoneNumber("111-222-5555");

        webTestClient.post()
                .uri("/api/patient/add")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(patientToSave))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED)
                .expectBody(Patient.class)
                .consumeWith(response -> {
                    Patient patientSaved = response.getResponseBody();
                    assertNotNull(patientSaved);
                    assertNotNull(patientSaved.getId());
                    assertEquals(patientToSave.getLastName(), patientSaved.getLastName());
                    assertEquals(patientToSave.getFirstName(), patientSaved.getFirstName());
                    assertEquals(patientToSave.getGender(), patientSaved.getGender());
                    assertEquals(patientToSave.getBirthdate().toString(), patientSaved.getBirthdate().toString());
                    assertEquals(patientToSave.getAddress(), patientSaved.getAddress());
                    assertEquals(patientToSave.getPhoneNumber(), patientSaved.getPhoneNumber());
                });
    }
}
