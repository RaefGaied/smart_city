package com.smartcity.ai.agentiaservice.config;

import com.smartcity.ai.agentiaservice.tools.CityTools;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

@Configuration
public class FunctionConfig {

    @Bean
    @Description("Récupère le statut du trafic routier pour une zone donnée (ex: Centre-Ville). Retourne l'état (FLUIDE, DENSE, etc.) et les incidents.")
    public java.util.function.Function<CityTools.TrafficRequest, String> getTrafficStatus(CityTools cityTools) {
        return cityTools.getTrafficStatus();
    }

    @Bean
    @Description("Récupère la consommation énergétique pour une zone donnée. Retourne les kW/h et le statut.")
    public java.util.function.Function<CityTools.EnergyRequest, String> getEnergyConsumption(CityTools cityTools) {
        return cityTools.getEnergyConsumption();
    }

    @Bean
    @Description("Liste toutes les zones de trafic avec leur statut actuel")
    public java.util.function.Function<CityTools.EmptyRequest, String> listAllTraffic(CityTools cityTools) {
        return cityTools.listAllTraffic();
    }

    @Bean
    @Description("Liste toutes les zones d'énergie avec leur consommation actuelle")
    public java.util.function.Function<CityTools.EmptyRequest, String> listAllEnergy(CityTools cityTools) {
        return cityTools.listAllEnergy();
    }
}
