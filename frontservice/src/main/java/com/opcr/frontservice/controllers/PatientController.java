package com.opcr.frontservice.controllers;

import com.opcr.frontservice.models.Note;
import com.opcr.frontservice.models.Patient;
import com.opcr.frontservice.models.Risk;
import com.opcr.frontservice.services.NoteService;
import com.opcr.frontservice.services.PatientService;
import com.opcr.frontservice.services.RiskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PatientController {

    @Value("${url.api.local}")
    private String urlLocal;

    @Autowired
    private PatientService patientService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private RiskService riskService;

    @GetMapping("/patient/{id}")
    public String getView(@PathVariable("id") Integer idPatient,
                          @RequestParam(value = "message", required = false) String message,
                          @RequestParam(value = "errorMessage", required = false) String errorMessage,
                          Model model) {
        if (idPatient != null) {

            Patient patient = patientService.getPatientWithId(idPatient);
            model.addAttribute("patient", patient);

            List<Note> listNote = noteService.getNotesForPatient(idPatient);
            model.addAttribute("notes", listNote);

            Risk risk = riskService.getRisk(idPatient);
            model.addAttribute("risk", risk.getRiskStatus().toUpperCase());

            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("message", message);
            return "patient";
        }
        return "patientAll";
    }

    @PostMapping("/patient/{id}/update")
    public String updatePatient(@PathVariable("id") Integer idPatient, @Valid Patient patient, BindingResult bindingResult, Model model) {

        patientService.updatePatient(patient, idPatient);

        return "redirect:" + urlLocal + "patient/" + idPatient + "?message=Data%20Updated.";
    }

    @PostMapping("/patient/{id}/addnote")
    public String addNote(@PathVariable("id") Integer idPatient, @RequestParam("textNote") String textNote, Model model) {
        Note newNote = new Note();
        newNote.setIdPatient(idPatient);
        newNote.setText(textNote);
        noteService.saveNote(newNote);

        return "redirect:" + urlLocal + "patient/" + idPatient + "?message=Note%20Added.";
    }
}
