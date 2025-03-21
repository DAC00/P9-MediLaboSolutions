package com.opcr.frontservice.services;

import com.opcr.frontservice.models.Note;
import org.springframework.beans.factory.annotation.Value;
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
     * @return a list of Notes.
     */
    public List<Note> getNotesForPatient(Integer idPatient) {
        return WebClient.create()
                .get()
                .uri(urlApiNote + "/" + idPatient)
                .retrieve()
                .bodyToFlux(Note.class)
                .doOnError(throwable -> System.out.println("ERROR: " + throwable))
                .collectList()
                .block();
    }

    /**
     * Save a Note for a Patient.
     *
     * @param noteToSave the Note to Save.
     */
    public void saveNote(Note noteToSave) {
        WebClient.create()
                .post()
                .uri(urlApiNote + "/add")
                .bodyValue(noteToSave)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
