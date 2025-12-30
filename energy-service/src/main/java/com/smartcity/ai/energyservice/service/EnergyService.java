package com.smartcity.ai.energyservice.service;

import com.smartcity.ai.energyservice.model.Energy;
import com.smartcity.ai.energyservice.repository.EnergyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnergyService {
    private final EnergyRepository repository;

    public EnergyService(EnergyRepository repository) {
        this.repository = repository;
    }

    // Lecture
    public List<Energy> getAll() {
        return repository.findAll();
    }

    public Energy getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Energy ID non trouvé: " + id));
    }

    public List<Energy> getByZone(String zone) {
        return repository.findByZone(zone);
    }

    public List<Energy> getByEnergyType(String energyType) {
        return repository.findByEnergyType(energyType);
    }

    public List<Energy> getByStatus(String status) {
        return repository.findByStatus(status);
    }

    // Écriture (Admin seulement)
    public Energy addEnergy(Energy energy) {
        if (energy.getTimestamp() == null) {
            energy.setTimestamp(LocalDateTime.now());
        }
        return repository.save(energy);
    }

    // Mise à jour
    public Energy updateEnergy(Long id, Double consumptionKwh, String status) {
        Energy energy = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Energy ID non trouvé: " + id));
        
        if (consumptionKwh != null) {
            energy.setConsumptionKwh(consumptionKwh);
        }
        if (status != null) {
            energy.setStatus(status);
        }
        energy.setTimestamp(LocalDateTime.now());
        
        return repository.save(energy);
    }
    
    // Suppression
    public void deleteEnergy(Long id) {
        repository.deleteById(id);
    }
}
