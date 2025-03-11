package com.opcr.noteservice.repositories;

import com.opcr.noteservice.models.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note, Integer> {
    List<Note> findNotesByIdPatient(Integer idPatient);
}
