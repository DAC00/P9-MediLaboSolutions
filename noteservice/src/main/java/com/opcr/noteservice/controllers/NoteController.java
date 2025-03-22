package com.opcr.noteservice.controllers;

import com.opcr.noteservice.models.Note;
import com.opcr.noteservice.services.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    /**
     * Get all the Note of the Patient with idPatient witch can be empty.
     * If data found the status is OK, if not NO CONTENT.
     *
     * @param idPatient of the Patient.
     * @return a ResponseEntity with a list of Note for the Patient.
     */
    @GetMapping("/{id}")
    public ResponseEntity<List<Note>> getNotesForPatient(@PathVariable("id") Integer idPatient) {
        logger.info("GET /id. Get a List<Note> for PATIENT with id {}.", idPatient);
        List<Note> notes = noteService.getNotesByPatientId(idPatient);
        if (notes.isEmpty()) {
            logger.info("GET /id. Get a List<Note> for PATIENT with id {}. No content found.", idPatient);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(notes);
        }
        logger.info("GET /id. Get a List<Note> for PATIENT with id {}. List of NOTE found.", idPatient);
        return ResponseEntity.ok(notes);
    }

    /**
     * Get all the texts from the Notes of a Patient witch can be empty.
     * If data found the status is OK, if not NO CONTENT.
     *
     * @param idPatient of the Patient.
     * @return a ResponseEntity with a list of text from the Notes.
     */
    @GetMapping("/text/{id}")
    public ResponseEntity<List<String>> getTextFromNotesForPatient(@PathVariable("id") Integer idPatient) {
        logger.info("GET /text/id. Get a List<String> from the NOTEs for PATIENT with id {}.", idPatient);
        List<String> textFromNotes = noteService.getTextFromNotes(idPatient);
        if (textFromNotes.isEmpty()) {
            logger.info("GET /text/id. Get a List<String> from the NOTEs for PATIENT with id {}. No content found.", idPatient);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(textFromNotes);
        }
        logger.info("GET /text/id. Get a List<String> from the NOTEs for PATIENT with id {}. List of String found.", idPatient);
        return ResponseEntity.ok(textFromNotes);
    }

    /**
     * Save a new Note.
     *
     * @param note to save.
     * @return a ResponseEntity with the note saved.
     */
    @PostMapping("/add")
    public ResponseEntity<Note> saveNote(@RequestBody Note note) {
        logger.info("POST /add Save a new NOTE: {}", note);
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.saveNote(note));
    }
}
