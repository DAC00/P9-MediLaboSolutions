package com.opcr.frontservice.controllers;

import com.opcr.frontservice.models.Patient;
import com.opcr.frontservice.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PatientListController {

    @Autowired
    private PatientService patientService;

    /**
     * Display the List of Patient page.
     *
     * @param message      an informative message.
     * @param errorMessage an error message.
     * @param token        JWToken of the User from the Cookie.
     * @param model        to pass data to the view.
     * @return name of the view.
     */
    @GetMapping("/patient/all")
    public String getView(@RequestParam(value = "message", required = false) String message,
                          @RequestParam(value = "errorMessage", required = false) String errorMessage,
                          @CookieValue(value = "JWT_TOKEN") String token,
                          Model model) {

        List<Patient> patientList = patientService.getPatients(token);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("patientList", patientList);
        return "patientList";
    }
}
