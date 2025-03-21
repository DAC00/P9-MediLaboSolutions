package com.opcr.frontservice.controllers;

import com.opcr.frontservice.models.Patient;
import com.opcr.frontservice.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PatientListController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/patient/all")
    public String getView(@RequestParam(value = "message", required = false) String message,
                          @RequestParam(value = "errorMessage", required = false) String errorMessage,
                          Model model) {
        List<Patient> patientList = patientService.getPatients();
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("patientList", patientList);
        return "patientList";
    }
}
