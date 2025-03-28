package com.opcr.frontservice.services;

import com.opcr.frontservice.models.Patient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
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
     * @param token JWToken of the User.
     * @return a list of Patient.
     */
    public List<Patient> getPatients(String token) {
        Mono<List<Patient>> list = WebClient.create()
                .get()
                .uri(urlApiPatient + "/all")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToFlux(Patient.class)
                .collectList();
        return list.block();
    }

    /**
     * Get the Patient with idPatient.
     *
     * @param idPatient id of the Patient.
     * @param token     JWToken of the User.
     * @return a Patient.
     */
    public Patient getPatientWithId(Integer idPatient, String token) {
        Mono<Patient> patient = WebClient.create()
                .get()
                .uri(urlApiPatient + "/" + idPatient)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(Patient.class);
        return patient.block();
    }

    /**
     * Save a new Patient.
     *
     * @param patientToSave the Patient to save.
     * @param token         JWToken of the User.
     */
    public void savePatient(Patient patientToSave, String token) {
        WebClient.create()
                .post()
                .uri(urlApiPatient + "/add")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .bodyValue(patientToSave)
                .retrieve()
                .toBodilessEntity().block();
    }

    /**
     * Update the data of a Patient.
     *
     * @param patientToUpdate Patient with new data.
     * @param idPatient       id of the Patient to update.
     * @param token           JWToken of the User.
     */
    public void updatePatient(Patient patientToUpdate, Integer idPatient, String token) {
        WebClient.create()
                .put()
                .uri(urlApiPatient + "/update/" + idPatient)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .bodyValue(patientToUpdate)
                .retrieve()
                .toBodilessEntity().block();
    }

}
