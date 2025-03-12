package com.opcr.riskservice.controllers;

import com.opcr.riskservice.models.Risk;
import com.opcr.riskservice.services.RiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/risk")
public class RiskController {

    @Autowired
    private RiskService riskService;

    /**
     * Get the Risk of the Patient with idPatient.
     *
     * @param idPatient id of the Patient.
     * @return the Risk.
     */
    @GetMapping("/{id}")
    public Risk getRiskForPatient(@PathVariable("id") Integer idPatient) {
        return riskService.evaluateRiskForPatient(idPatient);
    }
}
