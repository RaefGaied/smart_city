package com.smartcity.ai.trafficservice.service;

import com.smartcity.ai.trafficservice.client.EnergyClient;
import com.smartcity.ai.trafficservice.model.Traffic;
import com.smartcity.ai.trafficservice.repository.TrafficRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrafficService {
    private static final Logger log = LoggerFactory.getLogger(TrafficService.class);
    
    private final TrafficRepository repository;
    private final EnergyClient energyClient;

    public TrafficService(TrafficRepository repository, EnergyClient energyClient) {
        this.repository = repository;
        this.energyClient = energyClient;
    }

    // Lecture (Public ou User)
    public List<Traffic> getAll() {
        return repository.findAll();
    }

    public Traffic getStatus(String zone) {
        return repository.findByZone(zone)
                .orElseThrow(() -> new RuntimeException("Zone non trouvée: " + zone));
    }
    
    /**
     * Récupère les infos complètes (Trafic + Énergie) pour une zone
     * 
     * Circuit Breaker géré directement dans EnergyClient (Approche TP Section 6.2)
     * Le fallback est une default method dans l'interface Feign
     */
    public Map<String, Object> getTrafficWithEnergy(String zone) {
        log.info("Appel OpenFeign vers Energy Service pour la zone: {}", zone);
        
        // 1. Récupérer le trafic (toujours disponible localement)
        Traffic traffic = getStatus(zone);
        
        // 2. Appeler Energy Service via OpenFeign
        // Si le service est down, le Circuit Breaker dans EnergyClient retourne automatiquement
        // les données de fallback via la default method
        String energyData = energyClient.getEnergyByZone(zone);
        
        // 3. Combiner les données
        Map<String, Object> result = new HashMap<>();
        result.put("traffic", traffic);
        result.put("energy", energyData);
        result.put("source", "Services intégrés (OpenFeign + Circuit Breaker)");
        
        return result;
    }

    public Traffic getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trafic ID non trouvé: " + id));
    }

    // Écriture (Admin seulement)
    public Traffic addTraffic(Traffic traffic) {
        return repository.save(traffic);
    }

    // Mise à jour (Dynamic !)
    public Traffic updateTraffic(Long id, String newStatus, boolean incident) {
        Traffic traffic = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trafic ID non trouvé"));
        
        traffic.setStatus(newStatus);   // Ex: DENSE -> FLUID
        traffic.setIncident(incident);  // Ex: false -> true
        
        return repository.save(traffic);
    }
    
    // Suppression
    public void deleteTraffic(Long id) {
        repository.deleteById(id);
    }
}
