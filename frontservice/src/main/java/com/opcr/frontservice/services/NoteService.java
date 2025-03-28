package com.opcr.frontservice.services;

import com.opcr.frontservice.models.Note;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class NoteService {

    @Value("${url.api.note}")
    private String urlApiNote;

    /**
     * Get the list of all the Note for a Patient with idPatient.
     *
     * @param idPatient id of the Patient.
     * @param token     JWToken of the User.
     * @return a list of Notes.
     */
    public List<Note> getNotesForPatient(Integer idPatient, String token) {
        return WebClient.create()
                .get()
                .uri(urlApiNote + "/" + idPatient)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToFlux(Note.class)
                .doOnError(throwable -> System.out.println("ERROR: " + throwable))
                .collectList()
                .block();
    }

    /**
     * Save a Note for a Patient.
     *
     * @param token JWToken of the User.
     * @param noteToSave the Note to Save.
     */
    public void saveNote(Note noteToSave, String token) {
        WebClient.create()
                .post()
                .uri(urlApiNote + "/add")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .bodyValue(noteToSave)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
