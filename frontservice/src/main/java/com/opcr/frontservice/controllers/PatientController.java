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
import org.springframework.web.bind.annotation.*;

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

    /**
     * Display the Patient with idPatient page.
     *
     * @param idPatient    id of the Patient.
     * @param message      an informative message.
     * @param errorMessage an error message.
     * @param token        JWToken of the User from the Cookie.
     * @param model        to pass data to the view.
     * @return name of the view.
     */
    @GetMapping("/patient/{id}")
    public String getView(@PathVariable("id") Integer idPatient,
                          @RequestParam(value = "message", required = false) String message,
                          @RequestParam(value = "errorMessage", required = false) String errorMessage,
                          @CookieValue(value = "JWT_TOKEN") String token,
                          Model model) {

        if (idPatient != null) {
            Patient patient = patientService.getPatientWithId(idPatient, token);
            model.addAttribute("patient", patient);

            List<Note> listNote = noteService.getNotesForPatient(idPatient, token);
            model.addAttribute("notes", listNote);

            Risk risk = riskService.getRisk(idPatient, token);
            model.addAttribute("risk", risk.getRiskStatus().toUpperCase());

            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("message", message);
            return "patient";
        }
        return "patientAll";
    }

    /**
     * Update the data of a Patient.
     *
     * @param idPatient     id of the Patient.
     * @param patient       Patient to update.
     * @param bindingResult validation result.
     * @param token         JWToken of the User from the Cookie.
     * @param model         to pass data to the view.
     * @return redirect the User with a message.
     */
    @PostMapping("/patient/{id}/update")
    public String updatePatient(@PathVariable("id") Integer idPatient,
                                @Valid Patient patient,
                                BindingResult bindingResult,
                                @CookieValue(value = "JWT_TOKEN") String token,
                                Model model) {

        if(!bindingResult.hasErrors()){
            patientService.updatePatient(patient, idPatient, token);
            return "redirect:" + urlLocal + "patient/" + idPatient + "?message=Data%20Updated.";
        }
        return "redirect:"+urlLocal+"patient/"+idPatient + "?errorMessage=Data%20error.";
    }

    /**
     * Add/Save a new Note.
     *
     * @param idPatient id of the Patient.
     * @param textNote  content of the new Note.
     * @param token     JWToken of the User from the Cookie.
     * @param model     to pass data to the view.
     * @return redirect the User with a message.
     */
    @PostMapping("/patient/{id}/addnote")
    public String addNote(@PathVariable("id") Integer idPatient,
                          @RequestParam("textNote") String textNote,
                          @CookieValue(value = "JWT_TOKEN") String token,
                          Model model) {

        Note newNote = new Note();
        newNote.setIdPatient(idPatient);
        newNote.setText(textNote);
        noteService.saveNote(newNote, token);
        return "redirect:" + urlLocal + "patient/" + idPatient + "?message=Note%20Added.";
    }
}
