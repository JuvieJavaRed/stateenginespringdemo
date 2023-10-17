package com.thirdworddeveloper.stateengine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StateRequestDto {
    private String productName;
    private double balance;
    private String productType;
}
