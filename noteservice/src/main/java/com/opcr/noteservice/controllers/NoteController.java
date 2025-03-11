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

    @GetMapping("/all")
    public List<Note> getNotes() {
        return noteService.getNotes();
    }

    @GetMapping("/{id}")
    public List<Note> getNotesForPatient(@PathVariable("id") Integer idPatient) {
        return noteService.getNotesByPatientId(idPatient);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveNote(@RequestBody Note note) {
        noteService.saveNote(note);
    }
}
