package com.smartcity.ai.trafficservice.controller;

import com.smartcity.ai.trafficservice.dto.TrafficUpdateDto;
import com.smartcity.ai.trafficservice.model.Traffic;
import com.smartcity.ai.trafficservice.service.TrafficService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/traffic")
public class TrafficController {

    private final TrafficService service;

    public TrafficController(TrafficService service) {
        this.service = service;
    }

    // ✅ Accessible par tout le monde (User authentifié)
    @GetMapping
    public List<Traffic> getAll() {
        return service.getAll();
    }

    // GET par ID numérique : /traffic/id/1
    @GetMapping("/id/{id}")
    public Traffic getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // GET par zone : /traffic/zone/Paris
    @GetMapping("/zone/{zone}")
    public Traffic getByZone(@PathVariable String zone) {
        return service.getStatus(zone);
    }

    // 🔒 ADMIN SEULEMENT (Correction ici : on accepte ADMIN ou ROLE_ADMIN)
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasRole('ADMIN')")
    public Traffic create(@RequestBody Traffic traffic) {
        return service.addTraffic(traffic);
    }

    // 🔒 ADMIN SEULEMENT (Mise à jour dynamique)
    // Exemple Body: {"status":"BLOQUE","incident":true}
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasRole('ADMIN')")
    public Traffic update(@PathVariable Long id, @RequestBody TrafficUpdateDto updateDto) {
        return service.updateTraffic(id, updateDto.getStatus(), updateDto.isIncident());
    }

    // 🔒 ADMIN SEULEMENT
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        service.deleteTraffic(id);
    }
}