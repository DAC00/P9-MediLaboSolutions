package com.opcr.frontservice.services;

import com.opcr.frontservice.models.Patient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PatientService {

    @Value("${url.api.patient}")
    private String urlApiPatient;

    /**
     * Get a list of all the Patient.
     *
     * @return a list of Patient.
     */
    public List<Patient> getPatients() {
        Mono<List<Patient>> list = WebClient.create()
                .get()
                .uri(urlApiPatient + "/all")
                .retrieve()
                .bodyToFlux(Patient.class)
                .collectList();
        return list.block();
    }

    /**
     * Get the Patient with idPatient.
     *
     * @param idPatient id of the Patient.
     * @return a Patient.
     */
    public Patient getPatientWithId(Integer idPatient) {
        Mono<Patient> patient = WebClient.create()
                .get()
                .uri(urlApiPatient + "/" + idPatient)
                .retrieve()
                .bodyToMono(Patient.class);
        return patient.block();
    }

    /**
     * Save a new Patient.
     *
     * @param patientToSave the Patient to save.
     */
    public void savePatient(Patient patientToSave) {
        WebClient.create()
                .post()
                .uri(urlApiPatient + "/add")
                .bodyValue(patientToSave)
                .retrieve()
                .toBodilessEntity().block();
    }

    /**
     * Update the data of a Patient.
     *
     * @param patientToUpdate Patient with new data.
     * @param idPatient       id of the Patient to update.
     */
    public void updatePatient(Patient patientToUpdate, Integer idPatient) {
        WebClient.create()
                .put()
                .uri(urlApiPatient + "/update/" + idPatient)
                .bodyValue(patientToUpdate)
                .retrieve()
                .toBodilessEntity().block();
    }

}
