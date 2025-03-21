package com.opcr.patientservice.services;

import com.opcr.patientservice.models.Patient;
import com.opcr.patientservice.models.PatientInfoDTO;
import com.opcr.patientservice.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    /**
     * Return a list of all the Patient in the database.
     *
     * @return a list of Patient.
     */
    public List<Patient> getPatients() {
        return patientRepository.findAll();
    }

    /**
     * Return a Patient with the corresponding id.
     *
     * @param id of the Patient.
     * @return the Patient.
     */
    public Optional<Patient> getPatient(Integer id) {
        return patientRepository.findById(id);
    }

    /**
     * Get the birthdate and gender of a Patient with the id.
     *
     * @param id of the Patient.
     * @return a PatientInfoDTO.
     */
    public PatientInfoDTO getPatientBirthdateAndGender(Integer id) {
        Optional<Patient> patient = getPatient(id);
        return patient.map(value -> new PatientInfoDTO(value.getBirthdate(), value.getGender())).orElse(null);
    }

    /**
     * Add a Patient to the database.
     *
     * @param patientToAdd to the database.
     */
    public void addPatient(Patient patientToAdd) {
        patientRepository.save(patientToAdd);
    }

    /**
     * Update the information of the Patient with idPatient as id.
     *
     * @param idPatient       the id of the Patient to update.
     * @param patientToUpdate new data of the Patient.
     */
    public void updatePatient(Integer idPatient, Patient patientToUpdate) {
        if (getPatient(idPatient).isPresent() && idPatient.equals(patientToUpdate.getId())) {
            patientRepository.save(patientToUpdate);
        }
    }
}
