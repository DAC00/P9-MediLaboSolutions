package com.opcr.frontservice.controllers;

import com.opcr.frontservice.models.Patient;
import com.opcr.frontservice.services.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PatientAddController {

    @Value("${url.api.local}")
    private String urlLocal;

    @Autowired
    private PatientService patientService;

    /**
     * Display the Add Patient page.
     *
     * @param errorMessage an error message.
     * @param model        to pass data to the view.
     * @return name of the view.
     */
    @GetMapping("/patient/add")
    public String getView(@RequestParam(value = "errorMessage", required = false) String errorMessage, Model model) {
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("patient", new Patient());
        return "patientAdd";
    }

    /**
     * Add/Save a Patient if the required values are provided.
     *
     * @param message       an informative message.
     * @param errorMessage  an error message.
     * @param patient       Patient to add.
     * @param bindingResult validation result.
     * @param token         JWToken of the User from the Cookie.
     * @param model         to pass data to the view.
     * @return redirect the User with a message.
     */
    @PostMapping("/patient/add")
    public String addPatient(@RequestParam(value = "message", required = false) String message,
                             @RequestParam(value = "errorMessage", required = false) String errorMessage,
                             @Valid Patient patient, BindingResult bindingResult,
                             @CookieValue(value = "JWT_TOKEN") String token,
                             Model model) {
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        if (!bindingResult.hasErrors()) {
            patientService.savePatient(patient, token);
            return "redirect:" + urlLocal + "patient/all" + "?message=Patient%20Added.";
        }
        return "redirect:" + urlLocal + "patient/add" + "?errorMessage=Error.";
    }
}
