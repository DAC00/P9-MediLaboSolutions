package com.opcr.patientservice.controllers;

import com.opcr.patientservice.models.Patient;
import com.opcr.patientservice.models.PatientInfoDTO;
import com.opcr.patientservice.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    /**
     * Get all the Patient.
     *
     * @return all the Patient.
     */
    @GetMapping("/all")
    public List<Patient> getPatients() {
        return patientService.getPatients();
    }

    /**
     * Get the Patient with the idPatient.
     *
     * @param idPatient id of the Patient.
     * @return a Patient.
     */
    @GetMapping("/{id}")
    public Patient getPatient(@PathVariable("id") Integer idPatient) {
        Optional<Patient> patient = patientService.getPatient(idPatient);
        return patient.orElse(null);
    }

    /**
     * Get the birthdate and gender from a Patient with idPatient.
     *
     * @param idPatient id of the Patient.
     * @return a PatientInfoDTO.
     */
    @GetMapping("/info/{id}")
    public PatientInfoDTO getPatientBirthdateAndGender(@PathVariable("id") Integer idPatient) {
        return patientService.getPatientBirthdateAndGender(idPatient);
    }

    /**
     * Update the Patient with idPatient.
     *
     * @param idPatient id of the Patient.
     * @param patient   Patient with updated data.
     */
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePatient(@PathVariable("id") Integer idPatient, @RequestBody Patient patient) {
        patientService.updatePatient(idPatient, patient);
    }

    /**
     * Save a new Patient into the database.
     *
     * @param patient to save.
     */
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPatient(@RequestBody Patient patient) {
        patientService.addPatient(patient);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePatient(@PathVariable("id") Integer idPatient) {
        patientService.deletePatient(idPatient);
    }
}
