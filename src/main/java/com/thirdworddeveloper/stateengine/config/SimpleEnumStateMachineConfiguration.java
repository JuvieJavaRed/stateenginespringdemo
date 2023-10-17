package com.thirdworddeveloper.stateengine.config;

import com.thirdworddeveloper.stateengine.dto.OrderEvents;
import com.thirdworddeveloper.stateengine.dto.OrderStates;
import com.thirdworddeveloper.stateengine.dto.StateRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@Configuration
@EnableStateMachineFactory
@Slf4j
public class SimpleEnumStateMachineConfiguration extends StateMachineConfigurerAdapter<OrderStates, OrderEvents> {

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStates, OrderEvents> transitions) throws Exception {
        transitions.withExternal()
                .source(OrderStates.SUBMITTED).target(OrderStates.PAID).event(OrderEvents.PAY)
                .and()
                .withExternal().source(OrderStates.PAID).target(OrderStates.FULFILLED).event(OrderEvents.FULFILL)
                .and()
                .withExternal().source(OrderStates.SUBMITTED).target(OrderStates.CANCELLED).event(OrderEvents.CANCEL)
                .and()
                .withExternal().source(OrderStates.PAID).target(OrderStates.CANCELLED).event(OrderEvents.CANCEL);
    }

    @Override
    public void configure(StateMachineStateConfigurer<OrderStates, OrderEvents> states) throws Exception {
        states.withStates()
                .initial(OrderStates.SUBMITTED)
                .stateEntry(OrderStates.SUBMITTED, new Action<OrderStates, OrderEvents>() {
                    @Override
                    public void execute(StateContext<OrderStates, OrderEvents> stateContext) {
                        log.info("entering submitted state.....");
                        StateRequestDto stateRequestDto = (StateRequestDto) stateContext.getExtendedState().getVariables().get("stateObject");
                        log.info("The passed state object has the state name of : "+stateRequestDto.getProductName());
                    }
                })
                .state(OrderStates.PAID).stateEntry(OrderStates.PAID, new Action<OrderStates, OrderEvents>() {
                    @Override
                    public void execute(StateContext<OrderStates, OrderEvents> stateContext) {
                        log.info("We are now in the paid state");
                        StateRequestDto stateRequestDto = (StateRequestDto) stateContext.getExtendedState().getVariables().get("stateObject");
                        log.info("The passed state object in PAID has the state name of : "+stateRequestDto.getProductName());
                    }
                })
                .end(OrderStates.FULFILLED)
                .end(OrderStates.CANCELLED);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderStates, OrderEvents> config) throws Exception {

        StateMachineListenerAdapter<OrderStates, OrderEvents> adapter = new StateMachineListenerAdapter<OrderStates, OrderEvents>() {
            @Override
            public void stateChanged(State<OrderStates, OrderEvents> from, State<OrderStates, OrderEvents> to) {
                log.info("We have changed state from : "+from+ " to state : "+to);
            }
        };
        config.withConfiguration()
                .autoStartup(false)
                .listener(adapter);

    }


}
