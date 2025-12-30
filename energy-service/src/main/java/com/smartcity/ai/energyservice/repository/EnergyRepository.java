package com.smartcity.ai.energyservice.repository;

import com.smartcity.ai.energyservice.model.Energy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnergyRepository extends JpaRepository<Energy, Long> {
    List<Energy> findByZone(String zone);
    List<Energy> findByEnergyType(String energyType);
    List<Energy> findByStatus(String status);
}
