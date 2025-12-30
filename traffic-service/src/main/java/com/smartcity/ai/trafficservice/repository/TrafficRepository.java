package com.smartcity.ai.trafficservice.repository;

import com.smartcity.ai.trafficservice.model.Traffic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrafficRepository extends JpaRepository<Traffic, Long> {
    Optional<Traffic> findByZone(String zone);
}
