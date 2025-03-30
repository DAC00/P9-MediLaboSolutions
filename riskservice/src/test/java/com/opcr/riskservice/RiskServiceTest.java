package com.opcr.riskservice;

import com.opcr.riskservice.models.PatientInfoRisk;
import com.opcr.riskservice.models.Risk;
import com.opcr.riskservice.services.RiskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class RiskServiceTest {

    @Spy
    @InjectMocks
    private RiskService riskService;

    @Test
    public void calculateAgeFromBirthdateTest() {
        Date birthdate = Date.valueOf("2000-12-03");
        assertEquals(24, riskService.calculateAgeFromBirthdate(birthdate));
    }

    @Test
    public void findTheNumberOfTriggersTest() {
        List<String> listText = Arrays.asList("Taille Taille Réaction taille", "test", "POIDS test test fumeur.", "Hémoglobine A1C test");
        assertEquals(7, riskService.findTheNumberOfTriggers(listText));
    }

    @Test
    public void normalizeAndRemoveAccentsTest() {
        String wordToNormalize = "Réaction";
        String wordNormalize = riskService.normalizeAndRemoveAccents(wordToNormalize);
        assertEquals("Reaction", wordNormalize);
    }

    @Test
    public void countOccurrencesTest() {
        String text = "blabla test test truc blabla test";
        String wordToFind = "test";
        assertEquals(3, riskService.countOccurrences(text, wordToFind));
    }

    @Test
    public void evaluateRiskForPatientNoneTest() {
        PatientInfoRisk patientInfoRisk = new PatientInfoRisk("F", Date.valueOf("1950-12-03"));
        List<String> textNote = Arrays.asList("truc test truc test", "test", "none le test");
        Risk result = null;

        doReturn(patientInfoRisk).when(riskService).getPatientInfoRisk(anyInt(), anyString());
        doReturn(textNote).when(riskService).getTextNotes(anyInt(), anyString());

        result = riskService.evaluateRiskForPatient(anyInt(), anyString());

        assertNotNull(result);
        assertEquals("None", result.getRiskStatus());
    }

    @Test
    public void evaluateRiskForPatientBorderlineTest() {
        PatientInfoRisk patientInfoRisk = new PatientInfoRisk("M", Date.valueOf("1990-12-03"));
        List<String> textNote = Arrays.asList("POIDS test truc POIDS", "test", "none le test");
        Risk result = null;

        doReturn(patientInfoRisk).when(riskService).getPatientInfoRisk(anyInt(), anyString());
        doReturn(textNote).when(riskService).getTextNotes(anyInt(), anyString());

        result = riskService.evaluateRiskForPatient(anyInt(), anyString());

        assertNotNull(result);
        assertEquals("Borderline", result.getRiskStatus());
    }

    @Test
    public void evaluateRiskForPatientInDangerTest() {
        PatientInfoRisk patientInfoRisk = new PatientInfoRisk("F", Date.valueOf("2000-10-13"));
        List<String> textNote = Arrays.asList("truc POIDS truc POIDS", "POIDS", "POIDS le POIDS");
        Risk result = null;

        doReturn(patientInfoRisk).when(riskService).getPatientInfoRisk(anyInt(), anyString());
        doReturn(textNote).when(riskService).getTextNotes(anyInt(), anyString());

        result = riskService.evaluateRiskForPatient(anyInt(), anyString());

        assertNotNull(result);
        assertEquals("InDanger", result.getRiskStatus());
    }

    @Test
    public void evaluateRiskForPatientEarlyOnsetTest() {
        PatientInfoRisk patientInfoRisk = new PatientInfoRisk("M", Date.valueOf("2000-12-03"));
        List<String> textNote = Arrays.asList("Taille Taille Réaction taille", "test", "POIDS test test fumeur.", "Hémoglobine A1C test");
        Risk result = null;

        doReturn(patientInfoRisk).when(riskService).getPatientInfoRisk(anyInt(), anyString());
        doReturn(textNote).when(riskService).getTextNotes(anyInt(), anyString());

        result = riskService.evaluateRiskForPatient(anyInt(), anyString());

        assertNotNull(result);
        assertEquals("EarlyOnset", result.getRiskStatus());
    }
}
