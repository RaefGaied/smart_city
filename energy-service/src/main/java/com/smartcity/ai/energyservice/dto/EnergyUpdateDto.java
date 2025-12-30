package com.smartcity.ai.energyservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnergyUpdateDto {
    private Double consumptionKwh;
    private String status;
}
