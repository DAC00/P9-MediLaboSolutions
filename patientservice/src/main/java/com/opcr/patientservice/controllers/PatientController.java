package com.opcr.patientservice.controllers;

import com.opcr.patientservice.models.Patient;
import com.opcr.patientservice.models.PatientInfo;
import com.opcr.patientservice.services.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    /**
     * Get all the Patient.
     * If data found the status is OK, if not NO CONTENT.
     *
     * @return a ResponseEntity with a list of all the Patient.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Patient>> getPatients() {
        logger.info("GET /all. Get a list of PATIENT");
        List<Patient> patients = patientService.getPatients();
        if (patients.isEmpty()) {
            logger.info("GET /all. Get a list of PATIENT. No PATIENT found.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(patients);
        }
        logger.info("GET /all. Get a list of PATIENT. Content found.");
        return ResponseEntity.ok(patients);
    }

    /**
     * Get the Patient with the idPatient.
     * If a Patient is found the status is OK, if not the status is NOT FOUND.
     *
     * @param idPatient id of the Patient.
     * @return a ResponseEntity with a Patient.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable("id") Integer idPatient) {
        logger.info("GET /id. Get a PATIENT with id {}.", idPatient);
        Optional<Patient> patient = patientService.getPatient(idPatient);
        if (patient.isPresent()) {
            logger.info("GET /id. Get a PATIENT with id {}. PATIENT found.", idPatient);
            return ResponseEntity.ok(patient.get());
        } else {
            logger.info("GET /id. Get a PATIENT with id {}. PATIENT not found.", idPatient);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get the birthdate and gender from a Patient with idPatient.
     * If a Patient is found the status is OK, if not the status is NOT FOUND.
     *
     * @param idPatient id of the Patient.
     * @return a ResponseEntity with a PatientInfo.
     */
    @GetMapping("/info/{id}")
    public ResponseEntity<PatientInfo> getPatientBirthdateAndGender(@PathVariable("id") Integer idPatient) {
        logger.info("GET /info/id. Get a PatientInfo with id {}.", idPatient);
        Optional<PatientInfo> patientInfo = patientService.getPatientBirthdateAndGender(idPatient);
        if (patientInfo.isPresent()) {
            logger.info("GET /id. Get a PatientInfo with id {}. PatientInfo found.", idPatient);
            return ResponseEntity.ok(patientInfo.get());
        } else {
            logger.info("GET /id. Get a PatientInfo with id {}. PatientInfo not found.", idPatient);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update the Patient with idPatient.
     *
     * @param idPatient id of the Patient.
     * @param patient   Patient with updated data.
     * @return a ResponseEntity with a Patient updated.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable("id") Integer idPatient, @RequestBody Patient patient) {
        logger.info("PUT /update/id. Update a PATIENT with id {}. New data: {}", idPatient, patient);
        Optional<Patient> updatedPatient = patientService.updatePatient(idPatient, patient);
        if (updatedPatient.isPresent()) {
            logger.info("PUT /update/id. Update a PATIENT with id {}. Update completed.", idPatient);
            return ResponseEntity.ok(updatedPatient.get());
        } else {
            logger.info("PUT /update/id. Update a PATIENT with id {}. Patient not found.", idPatient);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Save a new Patient into the database.
     *
     * @param patient to save.
     * @return a ResponseEntity with the Patient saved.
     */
    @PostMapping("/add")
    public ResponseEntity<Patient> savePatient(@RequestBody Patient patient) {
        logger.info("POST /id. Save a new PATIENT with data {}.", patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.savePatient(patient));
    }
}
