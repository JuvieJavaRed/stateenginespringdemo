package com.thirdworddeveloper.stateengine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateResponseDto {
    private boolean success;
    private String message;
}
