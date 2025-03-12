package com.opcr.noteservice.controllers;

import com.opcr.noteservice.models.Note;
import com.opcr.noteservice.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    /**
     * Get all the Notes from de database.
     *
     * @return a list of all the Note.
     */
    @GetMapping("/all")
    public List<Note> getNotes() {
        return noteService.getNotes();
    }

    /**
     * Get all the Note of the Patient with idPatient.
     *
     * @param idPatient of the Patient.
     * @return a list of Note for the Patient.
     */
    @GetMapping("/{id}")
    public List<Note> getNotesForPatient(@PathVariable("id") Integer idPatient) {
        return noteService.getNotesByPatientId(idPatient);
    }

    /**
     * Get all the texts from the Notes of a Patient.
     *
     * @param idPatient of the Patient.
     * @return a list of text from the Notes.
     */
    @GetMapping("/text/{id}")
    public List<String> getTextFromNotesForPatient(@PathVariable("id") Integer idPatient) {
        return noteService.getTextFromNotes(idPatient);
    }

    /**
     * Save a new Note.
     *
     * @param note to save.
     */
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveNote(@RequestBody Note note) {
        noteService.saveNote(note);
    }
}
