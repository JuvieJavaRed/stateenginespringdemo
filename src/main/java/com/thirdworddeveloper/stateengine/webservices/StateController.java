package com.thirdworddeveloper.stateengine.webservices;

import com.thirdworddeveloper.stateengine.dto.StateRequestDto;
import com.thirdworddeveloper.stateengine.services.StateExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/state")
public class StateController {

    private final StateExecutionService stateExecutionService;

    @Autowired
    public StateController(StateExecutionService stateExecutionService) {
        this.stateExecutionService = stateExecutionService;
    }


    @PostMapping(value = "/createstate")
    public ResponseEntity<?> createState(@RequestBody @Validated StateRequestDto stateRequestDto) {
        return new ResponseEntity<>(stateExecutionService.runStateEngineTrial(stateRequestDto), HttpStatus.OK);
    }
}
