package com.opcr.patientservice.repositories;

import com.opcr.patientservice.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
}
