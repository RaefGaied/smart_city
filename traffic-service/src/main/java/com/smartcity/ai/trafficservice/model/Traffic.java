package com.smartcity.ai.trafficservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "traffic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Traffic {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String zone;
    
    @Column(nullable = false)
    private String status; // FLUID, DENSE, BLOQUE
    
    @Column(nullable = false)
    private boolean incident;
}
