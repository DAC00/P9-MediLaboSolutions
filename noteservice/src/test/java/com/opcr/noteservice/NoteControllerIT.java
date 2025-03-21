package com.opcr.noteservice;


import com.opcr.noteservice.models.Note;
import com.opcr.noteservice.repositories.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class NoteControllerIT {

    @Container
    @ServiceConnection
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setUpEach() {
        noteRepository.deleteAll();
        List<Note> notes = new ArrayList<>();
        notes.add(new Note("1", 1, "Test."));
        notes.add(new Note("2", 1, "One TEST."));
        notes.add(new Note("3", 2, "Two two."));
        notes.add(new Note("4", 3, "Test three."));
        noteRepository.saveAll(notes);
    }

    @Test
    public void getNotesTest() {
        ResponseEntity<List> response = restTemplate.getForEntity("/api/note/all", List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(4, response.getBody().size());
    }

    @Test
    public void getNotesForPatientsTest() {
        ResponseEntity<List> response = restTemplate.getForEntity("/api/note/1", List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void saveNoteTest() {
        Note newNote = new Note("5", 4, "NewNew");
        ResponseEntity<?> response = restTemplate.postForEntity("/api/note/add", newNote, Void.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(5, noteRepository.findAll().size());
    }
}
