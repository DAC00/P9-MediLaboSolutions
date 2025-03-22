package com.opcr.riskservice.controllers;

import com.opcr.riskservice.models.Risk;
import com.opcr.riskservice.services.RiskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/risk")
public class RiskController {

    @Autowired
    private RiskService riskService;

    private static final Logger logger = LoggerFactory.getLogger(RiskController.class);

    /**
     * Get the Risk of the Patient with idPatient.
     *
     * @param idPatient id of the Patient.
     * @return the Risk.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Risk> getRiskForPatient(@PathVariable("id") Integer idPatient) {
        logger.info("GET /id Get a Risk for Patient with id {}", idPatient);
        return ResponseEntity.ok(riskService.evaluateRiskForPatient(idPatient));
    }
}
