package com.smartcity.ai.agentiaservice.tools;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.function.Function;

@Component
public class CityTools {

    private final RestClient restClient;

    public CityTools(RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    // --- OUTIL 1 : TRAFIC ---
    public Function<TrafficRequest, String> getTrafficStatus() {
        return request -> {
            try {
                // Appel au microservice Traffic (Port 9091)
                // Note: En prod, on utiliserait le nom du service via Eureka
                String response = restClient.get()
                        .uri("http://localhost:9091/traffic/zone/" + request.zone())
                        .retrieve()
                        .body(String.class);
                return response != null ? response : "Aucune donnée pour la zone " + request.zone();
            } catch (Exception e) {
                return "Désolé, impossible de joindre le service Trafic pour la zone " + request.zone() + ". Erreur: " + e.getMessage();
            }
        };
    }

    // --- OUTIL 2 : ÉNERGIE ---
    public Function<EnergyRequest, String> getEnergyConsumption() {
        return request -> {
            try {
                // Appel au microservice Energy (Port 9092)
                String response = restClient.get()
                        .uri("http://localhost:9092/energy/zone/" + request.zone())
                        .retrieve()
                        .body(String.class);
                return response != null ? response : "Aucune donnée pour la zone " + request.zone();
            } catch (Exception e) {
                return "Info indisponible pour l'énergie dans la zone " + request.zone() + ". Erreur: " + e.getMessage();
            }
        };
    }

    // --- OUTIL 3 : LISTE TOUTES LES ZONES TRAFIC ---
    public Function<EmptyRequest, String> listAllTraffic() {
        return request -> {
            try {
                String response = restClient.get()
                        .uri("http://localhost:9091/traffic")
                        .retrieve()
                        .body(String.class);
                return response != null ? response : "Aucune donnée disponible";
            } catch (Exception e) {
                return "Impossible de récupérer les données de trafic. Erreur: " + e.getMessage();
            }
        };
    }

    // --- OUTIL 4 : LISTE TOUTES LES ZONES ÉNERGIE ---
    public Function<EmptyRequest, String> listAllEnergy() {
        return request -> {
            try {
                String response = restClient.get()
                        .uri("http://localhost:9092/energy")
                        .retrieve()
                        .body(String.class);
                return response != null ? response : "Aucune donnée disponible";
            } catch (Exception e) {
                return "Impossible de récupérer les données d'énergie. Erreur: " + e.getMessage();
            }
        };
    }

    // Records pour les paramètres des fonctions
    public record TrafficRequest(String zone) {}
    public record EnergyRequest(String zone) {}
    public record EmptyRequest() {}
}
