package com.smartcity.ai.trafficservice.service;

import com.smartcity.ai.trafficservice.model.Traffic;
import com.smartcity.ai.trafficservice.repository.TrafficRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrafficService {
    private final TrafficRepository repository;

    public TrafficService(TrafficRepository repository) {
        this.repository = repository;
    }

    // Lecture (Public ou User)
    public List<Traffic> getAll() {
        return repository.findAll();
    }

    public Traffic getStatus(String zone) {
        return repository.findByZone(zone)
                .orElseThrow(() -> new RuntimeException("Zone non trouvée: " + zone));
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
