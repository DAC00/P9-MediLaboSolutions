package com.opcr.frontservice.services;

import com.opcr.frontservice.models.Risk;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RiskService {

    @Value("${url.api.risk}")
    private String urlApiRisk;

    /**
     * Get the Risk for a Patient with idPatient.
     *
     * @param idPatient id of the Patient.
     * @return Risk for a Patient.
     */
    public Risk getRisk(Integer idPatient) {
        Mono<Risk> risk = WebClient.create()
                .get()
                .uri(urlApiRisk + "/" + idPatient)
                .retrieve()
                .bodyToMono(Risk.class);
        return risk.block();
    }
}
