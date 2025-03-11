package com.opcr.patientservice.controllers;

import com.opcr.patientservice.models.Patient;
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

    @GetMapping("/all")
    public List<Patient> getPatients() {
        return patientService.getPatients();
    }

    @GetMapping("/{id}")
    public Patient getPatient(@PathVariable("id") Integer idPatient) {
        Optional<Patient> patient = patientService.getPatient(idPatient);
        return patient.orElse(null);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePatient(@PathVariable("id") Integer idPatient, @RequestBody Patient patient) {
        patientService.updatePatient(idPatient, patient);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPatient(@RequestBody Patient patient){
        patientService.addPatient(patient);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePatient(@PathVariable("id") Integer idPatient){
        patientService.deletePatient(idPatient);
    }
}
