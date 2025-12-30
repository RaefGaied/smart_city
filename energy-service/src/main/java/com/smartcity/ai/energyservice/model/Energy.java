package com.smartcity.ai.energyservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "energy")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Energy {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String zone;
    
    @Column(nullable = false)
    private Double consumptionKwh;
    
    @Column(nullable = false)
    private String energyType; // SOLAR, WIND, ELECTRIC, GAS
    
    @Column(nullable = false)
    private String status; // NORMAL, ECONOMY, OVERLOAD
    
    @Column(nullable = false)
    private LocalDateTime timestamp;
}
