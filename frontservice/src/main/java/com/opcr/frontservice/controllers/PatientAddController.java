package com.opcr.frontservice.controllers;

import com.opcr.frontservice.models.Patient;
import com.opcr.frontservice.services.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PatientAddController {

    @Value("${url.api.local}")
    private String urlLocal;

    @Autowired
    private PatientService patientService;

    @GetMapping("/patient/add")
    public String getView(Model model) {
        model.addAttribute("patient", new Patient());
        return "patientAdd";
    }

    @PostMapping("/patient/add")
    public String addPatient(@RequestParam(value = "message", required = false) String message,
                             @RequestParam(value = "errorMessage", required = false) String errorMessage,
                             @Valid Patient patient, BindingResult bindingResult,
                             Model model) {
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        if (!bindingResult.hasErrors()) {
            patientService.savePatient(patient);
            return "redirect:" + urlLocal + "patient/all" + "?message=Patient%20Added.";
        }
        return "redirect:" + urlLocal + "patient/add" + "?errorMessage=Error.";
    }
}
