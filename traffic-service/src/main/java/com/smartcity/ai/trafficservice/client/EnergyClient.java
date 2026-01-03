package com.smartcity.ai.trafficservice.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Client Feign pour appeler le Energy Service
 * Découverte automatique via Eureka (pas d'URL en dur)
 * 
 * Circuit Breaker intégré avec Default Method (Approche TP Section 6.2)
 */
@FeignClient(name = "ENERGY-SERVICE")
public interface EnergyClient {

    /**
     * Récupère les données d'énergie pour une zone
     * Circuit Breaker activé : en cas d'échec, appelle getDefaultEnergy()
     * 
     * @param zone nom de la zone
     * @return JSON de la consommation d'énergie
     */
    @GetMapping("/energy/zone/{zone}")
    @CircuitBreaker(name = "energy-service-cb", fallbackMethod = "getDefaultEnergy")
    String getEnergyByZone(@PathVariable("zone") String zone);
    
    /**
     * METHODE PAR DEFAUT (FALLBACK) - TP Section 6.2
     * Utilisée quand Energy Service est indisponible
     * La signature doit être identique + paramètre Exception
     * 
     * @param zone nom de la zone demandée
     * @param exception l'exception qui a déclenché le fallback
     * @return message de service indisponible en JSON
     */
    default String getDefaultEnergy(String zone, Exception exception) {
        return "[{\"zone\":\"" + zone + "\",\"status\":\"Service Énergie indisponible\",\"message\":\"Mode dégradé - Circuit Breaker activé\"}]";
    }
}
