package com.opcr.riskservice.services;

import com.opcr.riskservice.models.PatientInfoDTO;
import com.opcr.riskservice.models.Risk;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;

@Service
public class RiskService {

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
                risk.setRiskStatus("Early onset");
            } else if (nbTriggers >= 6) {
                risk.setRiskStatus("In Danger");
            } else if (nbTriggers >= 2){
                risk.setRiskStatus("Borderline");
            }
        } else {
            if (patientInfo.getGender().equals("M")) {
                if (nbTriggers >= 5) {
                    risk.setRiskStatus("Early onset");
                } else if (nbTriggers >= 3) {
                    risk.setRiskStatus("In Danger");
                }
            } else {
                if (nbTriggers >= 7) {
                    risk.setRiskStatus("Early onset");
                } else if (nbTriggers >= 5) {
                    risk.setRiskStatus("In Danger");
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
    private int calculateAgeFromBirthdate(Date birthdate) {
        return Period.between(LocalDate.now(), birthdate.toLocalDate()).getYears();
    }

    /**
     * Find the number of the trigger in the list textFromNotes.
     *
     * @param textFromNotes the content of the Notes.
     * @return number of triggers in the list textFromNotes.
     */
    private int findTheNumberOfTriggers(List<String> textFromNotes) {
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
            for (String trigger : triggers) {
                if (text.toUpperCase().contains(trigger.toUpperCase())) nbTriggers++;
            }
        }
        return nbTriggers;
    }

    /**
     * Get information from the PatientService into aPatientInfoDTO.
     *
     * @param idPatient id of the Patient.
     * @return a PatientInfoDTO witch contains age and gender.
     */
    private PatientInfoDTO getPatientInfo(Integer idPatient) {
        return null;
    }

    /**
     * Get the all the text from the Notes for a Patient with idPatient.
     *
     * @param idPatient id of the Patient.
     * @return the content of the Notes.
     */
    private List<String> getTextNote(Integer idPatient) {
        return null;
    }
}
