package com.opcr.riskservice.services;

import com.opcr.riskservice.models.PatientInfoDTO;
import com.opcr.riskservice.models.Risk;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.sql.Date;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;

@Service
public class RiskService {

    @Value("${url.api.patient}")
    private String urlApiPatient;

    @Value("${url.api.note}")
    private String urlApiNote;

    /**
     * Evaluate the Risk for a Patient with idPatient of developing diabetes.
     *
     * @param idPatient id of the Patient.
     * @return the evaluated Risk.
     */
    public Risk evaluateRiskForPatient(Integer idPatient) {
        PatientInfoDTO patientInfo = getPatientInfo(idPatient);

        int age = calculateAgeFromBirthdate(patientInfo.getBirthdate());
        int nbTriggers = findTheNumberOfTriggers(getTextNote(idPatient));

        Risk risk = new Risk();
        risk.setIdPatient(idPatient);
        risk.setRiskStatus("None");

        if (age >= 30) {
            if (nbTriggers >= 8) {
                risk.setRiskStatus("EarlyOnset");
            } else if (nbTriggers >= 6) {
                risk.setRiskStatus("InDanger");
            } else if (nbTriggers >= 2) {
                risk.setRiskStatus("Borderline");
            }
        } else {
            if (patientInfo.getGender().equals("M")) {
                if (nbTriggers >= 5) {
                    risk.setRiskStatus("EarlyOnset");
                } else if (nbTriggers >= 3) {
                    risk.setRiskStatus("InDanger");
                }
            } else {
                if (nbTriggers >= 7) {
                    risk.setRiskStatus("EarlyOnset");
                } else if (nbTriggers >= 5) {
                    risk.setRiskStatus("InDanger");
                }
            }
        }
        return risk;
    }

    /**
     * Return the age of the Patient from his birthdate.
     *
     * @param birthdate of the Patient.
     * @return the age.
     */
    public int calculateAgeFromBirthdate(Date birthdate) {
        return Math.abs(Period.between(LocalDate.now(), birthdate.toLocalDate()).getYears());
    }

    /**
     * Find the number of the trigger in the list textFromNotes.
     *
     * @param textFromNotes the content of the Notes.
     * @return number of triggers in the list textFromNotes.
     */
    public int findTheNumberOfTriggers(List<String> textFromNotes) {
        List<String> triggers = Arrays.asList("Hémoglobine A1C"
                , "Microalbumine"
                , "Taille"
                , "Poids"
                , "Fumeur"
                , "Fumeuse"
                , "Anormal"
                , "Cholestérol"
                , "Vertiges"
                , "Rechute"
                , "Réaction"
                , "Anticorps");

        int nbTriggers = 0;

        for (String text : textFromNotes) {
            String textLower = normalizeAndRemoveAccents(text.toLowerCase());
            for (String trigger : triggers) {
                String triggerLower = normalizeAndRemoveAccents(trigger.toLowerCase());
                if (textLower.contains(triggerLower)) {
                    nbTriggers += countOccurrences(textLower, triggerLower);
                }
            }
        }
        return nbTriggers;
    }

    /**
     * Count the number of times a word appears in a text.
     *
     * @param text       source.
     * @param wordToFind word to search for.
     * @return number of appearance.
     */
    public int countOccurrences(String text, String wordToFind) {
        int count = 0;
        int index = 0;
        while ((index = text.indexOf(wordToFind, index)) != -1) {
            count++;
            index += wordToFind.length();
        }
        return count;
    }

    /**
     * Normalize and remove the accents of the input.
     *
     * @param input String to use.
     * @return a String normalize without accents.
     */
    public String normalizeAndRemoveAccents(String input) {
        return input == null ? null :
                Normalizer.normalize(input, Normalizer.Form.NFD)
                        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    /**
     * Get information from the PatientService into aPatientInfoDTO.
     *
     * @param idPatient id of the Patient.
     * @return a PatientInfoDTO witch contains age and gender.
     */
    public PatientInfoDTO getPatientInfo(Integer idPatient) {
        Mono<PatientInfoDTO> mono = WebClient.create()
                .get()
                .uri(urlApiPatient + idPatient)
                .retrieve()
                .bodyToMono(PatientInfoDTO.class);
        return mono.block();
    }

    /**
     * Get the all the text from the Notes for a Patient with idPatient.
     *
     * @param idPatient id of the Patient.
     * @return the content of the Notes.
     */
    public List<String> getTextNote(Integer idPatient) {
        Mono<List<String>> mono = WebClient.create()
                .get()
                .uri(urlApiNote + idPatient)
                .retrieve()
                .bodyToFlux(String.class)
                .collectList();
        return mono.block();
    }
}
