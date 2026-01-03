package com.smartcity.ai.agentiaservice.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CityTools {

    private final RestClient restClient;

    public CityTools(RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    @Tool(name = "getTrafficStatus",
            description = "Récupère le statut du trafic routier pour une zone donnée (ex: Centre-Ville, Nord, Sud, Est, Ouest). Retourne l'état (FLUIDE, DENSE, BLOQUE) et les incidents.")
    public String getTrafficStatus(
            @ToolParam(description = "Nom de la zone à consulter") String zone) {
        try {
            String response = restClient.get()
                    .uri("http://localhost:9091/traffic/zone/" + zone)
                    .retrieve()
                    .body(String.class);
            return response != null ? response : "Aucune donnée pour la zone " + zone;
        } catch (Exception e) {
            return "Désolé, impossible de joindre le service Trafic pour la zone " + zone + ". Erreur: " + e.getMessage();
        }
    }

    @Tool(name = "getEnergyConsumption",
            description = "Récupère la consommation énergétique pour une zone donnée. Retourne les kW/h et le type d'énergie.")
    public String getEnergyConsumption(
            @ToolParam(description = "Nom de la zone à consulter") String zone) {
        try {
            String response = restClient.get()
                    .uri("http://localhost:9092/energy/zone/" + zone)
                    .retrieve()
                    .body(String.class);
            return response != null ? response : "Aucune donnée pour la zone " + zone;
        } catch (Exception e) {
            return "Info indisponible pour l'énergie dans la zone " + zone + ". Erreur: " + e.getMessage();
        }
    }

    @Tool(name = "listAllTraffic",
            description = "Liste toutes les zones de trafic avec leur statut actuel (FLUIDE, DENSE, BLOQUE) et les incidents")
    public String listAllTraffic() {
        try {
            String response = restClient.get()
                    .uri("http://localhost:9091/traffic")
                    .retrieve()
                    .body(String.class);
            return response != null ? response : "Aucune donnée disponible";
        } catch (Exception e) {
            return "Impossible de récupérer les données de trafic. Erreur: " + e.getMessage();
        }
    }

    @Tool(name = "listAllEnergy",
            description = "Liste toutes les zones d'énergie avec leur consommation actuelle en kW/h")
    public String listAllEnergy() {
        try {
            String response = restClient.get()
                    .uri("http://localhost:9092/energy")
                    .retrieve()
                    .body(String.class);
            return response != null ? response : "Aucune donnée disponible";
        } catch (Exception e) {
            return "Impossible de récupérer les données d'énergie. Erreur: " + e.getMessage();
        }
    }
}
