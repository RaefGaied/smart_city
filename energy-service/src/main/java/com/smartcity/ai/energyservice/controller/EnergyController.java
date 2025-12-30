package com.smartcity.ai.energyservice.controller;

import com.smartcity.ai.energyservice.dto.EnergyUpdateDto;
import com.smartcity.ai.energyservice.model.Energy;
import com.smartcity.ai.energyservice.service.EnergyService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/energy")
public class EnergyController {

    private final EnergyService service;

    public EnergyController(EnergyService service) {
        this.service = service;
    }

    // ✅ Accessible par tout le monde
    @GetMapping
    public List<Energy> getAll() {
        return service.getAll();
    }

    @GetMapping("/id/{id}")
    public Energy getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/zone/{zone}")
    public List<Energy> getByZone(@PathVariable String zone) {
        return service.getByZone(zone);
    }

    @GetMapping("/type/{energyType}")
    public List<Energy> getByType(@PathVariable String energyType) {
        return service.getByEnergyType(energyType);
    }

    @GetMapping("/status/{status}")
    public List<Energy> getByStatus(@PathVariable String status) {
        return service.getByStatus(status);
    }

    // 🔒 ADMIN SEULEMENT
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasRole('ADMIN')")
    public Energy create(@RequestBody Energy energy) {
        return service.addEnergy(energy);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasRole('ADMIN')")
    public Energy update(@PathVariable Long id, @RequestBody EnergyUpdateDto updateDto) {
        return service.updateEnergy(id, updateDto.getConsumptionKwh(), updateDto.getStatus());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        service.deleteEnergy(id);
    }
}
