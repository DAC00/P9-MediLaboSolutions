package com.opcr.frontservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    /**
     * Display the login page.
     *
     * @param message      an informative message.
     * @param errorMessage an error message.
     * @param model        to pass data to the view.
     * @return name of the view.
     */
    @GetMapping("/login")
    public String getView(@RequestParam(value = "message", required = false) String message,
                          @RequestParam(value = "errorMessage", required = false) String errorMessage,
                          Model model) {

        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }
}
