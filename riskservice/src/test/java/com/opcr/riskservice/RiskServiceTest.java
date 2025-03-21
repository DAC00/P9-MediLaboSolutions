package com.opcr.riskservice;

import com.opcr.riskservice.services.RiskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RiskServiceTest {

    private final RiskService riskService = new RiskService();

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
    public void evaluateRiskForPatientTest() {

    }
}
