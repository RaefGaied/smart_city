package com.smartcity.ai.trafficservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrafficUpdateDto {
    private String status;
    private boolean incident;
}
