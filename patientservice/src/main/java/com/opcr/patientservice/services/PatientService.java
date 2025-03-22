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
    public Optional<PatientInfoDTO> getPatientBirthdateAndGender(Integer id) {
        Optional<Patient> patient = getPatient(id);
        return patient.map(value -> new PatientInfoDTO(value.getBirthdate(), value.getGender()));
    }

    /**
     * Save a Patient to the database.
     *
     * @param patientToSave to the database.
     * @return the Patient saved.
     */
    public Patient savePatient(Patient patientToSave) {
        return patientRepository.save(patientToSave);
    }

    /**
     * Update the information of the Patient with idPatient as id.
     *
     * @param idPatient      the id of the Patient to update.
     * @param patientUpdated new data of the Patient.
     * @return a Patient with updated data.
     */
    public Optional<Patient> updatePatient(Integer idPatient, Patient patientUpdated) {
        return getPatient(idPatient).map(patientToUpdate -> {
            patientToUpdate.setLastName(patientUpdated.getLastName());
            patientToUpdate.setFirstName(patientUpdated.getFirstName());
            patientToUpdate.setBirthdate(patientUpdated.getBirthdate());
            patientToUpdate.setGender(patientUpdated.getGender());
            patientToUpdate.setAddress(patientUpdated.getAddress());
            patientToUpdate.setPhoneNumber(patientUpdated.getPhoneNumber());
            return patientRepository.save(patientToUpdate);
        });
    }
}
