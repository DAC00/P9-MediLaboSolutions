package com.opcr.noteservice.services;

import com.opcr.noteservice.models.Note;
import com.opcr.noteservice.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    /**
     * Get all the Notes from the database.
     *
     * @return the list of all the Note.
     */
    public List<Note> getNotes() {
        return noteRepository.findAll();
    }

    /**
     * Get all the Note for a Patient with patientId.
     *
     * @param idPatient id of a Patient.
     * @return all the Note for the Patient.
     */
    public List<Note> getNotesByPatientId(Integer idPatient) {
        return noteRepository.findNotesByIdPatient(idPatient);
    }

    /**
     * Save a Note in the database.
     *
     * @param noteToSave is the Note to save.
     */
    public void saveNote(Note noteToSave) {
        noteRepository.save(noteToSave);
    }
}
