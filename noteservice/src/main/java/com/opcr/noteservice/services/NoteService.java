package com.opcr.noteservice.services;

import com.opcr.noteservice.models.Note;
import com.opcr.noteservice.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

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
     * Get all the text from the Notes of the Patient with idPatient.
     *
     * @param idPatient id of the Patient.
     * @return a list of text from Notes.
     */
    public List<String> getTextFromNotes(Integer idPatient) {
        List<String> textFromNote = new ArrayList<>();
        for (Note note : getNotesByPatientId(idPatient)) {
            textFromNote.add(note.getText());
        }
        return textFromNote;
    }

    /**
     * Save a Note in the database.
     *
     * @param noteToSave is the Note to save.
     * @return the Note saved.
     */
    public Note saveNote(Note noteToSave) {
        return noteRepository.save(noteToSave);
    }
}
