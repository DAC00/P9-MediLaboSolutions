package com.opcr.noteservice;


import com.opcr.noteservice.models.Note;
import com.opcr.noteservice.repositories.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureWebTestClient
public class NoteControllerIT {

    @Container
    @ServiceConnection
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private WebTestClient webTestClient;

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
    public void getNotesForPatientsOkTest() {
        webTestClient.get()
                .uri("/api/note/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBodyList(Note.class)
                .consumeWith(response -> {
                    List<Note> notes = response.getResponseBody();
                    assertNotNull(notes);
                    assertEquals(2, notes.size());
                });
    }

    @Test
    public void getNotesForPatientsNoContentTest() {
        webTestClient.get()
                .uri("/api/note/5")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NO_CONTENT)
                .expectBodyList(Note.class)
                .consumeWith(response -> {
                    List<Note> notes = response.getResponseBody();
                    assertNotNull(notes);
                    assertEquals(Collections.EMPTY_LIST, notes);
                });
    }

    @Test
    public void getTextFromNotesForPatientOkTest() {
        webTestClient.get()
                .uri("/api/note/text/2")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBodyList(String.class)
                .consumeWith(response -> {
                    List<String> textFromNotes = response.getResponseBody();
                    assertNotNull(textFromNotes);
                    assertEquals(1, textFromNotes.size());
                });
    }

    @Test
    public void getTextFromNotesForPatientNoContentTest() {
        webTestClient.get()
                .uri("/api/note/text/100")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NO_CONTENT)
                .expectBodyList(String.class)
                .consumeWith(response -> {
                    List<String> textFromNotes = response.getResponseBody();
                    assertNotNull(textFromNotes);
                    assertEquals(Collections.EMPTY_LIST, textFromNotes);
                });
    }

    @Test
    public void saveNoteTest() {
        Note noteToSave = new Note("5", 4, "NewNew");
        webTestClient.post()
                .uri("/api/note/add")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(noteToSave))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED)
                .expectBody(Note.class)
                .consumeWith(response -> {
                    Note noteSaved = response.getResponseBody();
                    assertNotNull(noteSaved);
                    assertNotNull(noteSaved.getId());
                    assertEquals(noteToSave.getIdPatient(), noteSaved.getIdPatient());
                    assertEquals(noteToSave.getText(), noteSaved.getText());
                });
    }
}
