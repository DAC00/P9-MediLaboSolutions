package com.opcr.noteservice;

import com.opcr.noteservice.models.Note;
import com.opcr.noteservice.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NoteServiceApplication implements CommandLineRunner {

    @Autowired
    private NoteRepository noteRepository;

    public static void main(String[] args) {
        SpringApplication.run(NoteServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Init.");
        if (noteRepository.count() > 0) {
            System.out.println("Data present.");
        } else {
            noteRepository.save(new Note("1", 1, "Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé."));
            noteRepository.save(new Note("2", 2, "Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement."));
            noteRepository.save(new Note("3", 2, "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois Il remarque également que son audition continue d'être anormale."));
            noteRepository.save(new Note("4", 3, "Le patient déclare qu'il fume depuis peu."));
            noteRepository.save(new Note("5", 3, "Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière Il se plaint également de crises d’apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé."));
            noteRepository.save(new Note("6", 4, "Le patient déclare qu'il lui est devenu difficile de monter les escaliers Il se plaint également d’être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments."));
            noteRepository.save(new Note("7", 4, "Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps."));
            noteRepository.save(new Note("8", 4, "Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé."));
            noteRepository.save(new Note("9", 4, "Taille, Poids, Cholestérol, Vertige et Réaction."));
        }
        noteRepository.findAll().forEach(note -> System.out.println(note.toString()));
        System.out.println("Init db complete.");
    }
}
