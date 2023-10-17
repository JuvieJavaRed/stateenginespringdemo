package com.thirdworddeveloper.stateengine.services.impl;

import com.thirdworddeveloper.stateengine.dto.OrderEvents;
import com.thirdworddeveloper.stateengine.dto.OrderStates;
import com.thirdworddeveloper.stateengine.dto.StateRequestDto;
import com.thirdworddeveloper.stateengine.dto.StateResponseDto;
import com.thirdworddeveloper.stateengine.services.StateExecutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StateExecutionServiceImpl implements StateExecutionService {

    private final StateMachineFactory<OrderStates, OrderEvents> factory;

    @Autowired
    public StateExecutionServiceImpl(StateMachineFactory<OrderStates, OrderEvents> factory) {
        this.factory = factory;
    }

    @Override
    public StateResponseDto runStateEngineTrial(StateRequestDto stateRequestDto) {
        try {
            log.info("now processing state engine for the state represented by "+stateRequestDto.getProductName());
            StateMachine<OrderStates, OrderEvents> machine = this.factory.getStateMachine("1234567");
            machine.getExtendedState().getVariables().put("stateObject", stateRequestDto);
            machine.start();
            log.info("machine has started and the state is : "+machine.getState().getId().name());
            machine.sendEvent(OrderEvents.PAY);
            log.info("The state of the job is now "+machine.getState().getId().name());
            machine.sendEvent(OrderEvents.FULFILL);
            log.info("The state of the job is at the end and is : "+machine.getState().getId().name());
            return new StateResponseDto(true, "You did a thing");
        } catch (Exception ex) {
            throw ex;
        }
    }
}
