package com.thirdworddeveloper.stateengine.services;

import com.thirdworddeveloper.stateengine.dto.StateRequestDto;
import com.thirdworddeveloper.stateengine.dto.StateResponseDto;

public interface StateExecutionService {

    /**
     * Service to try out the state engine process and see how it works
     * @param stateRequestDto
     * @return
     */
    public StateResponseDto runStateEngineTrial(StateRequestDto stateRequestDto);
}
