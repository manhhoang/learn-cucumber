package com.intilery.exercise.web.facade;

import com.intilery.exercise.core.processing.usecase.DoProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/process")
public class ProcessingResource {
    private final DoProcessing doProcessing;

    @Autowired
    public ProcessingResource(DoProcessing doProcessing) {
        this.doProcessing = doProcessing;
    }

    @RequestMapping(value = "/single", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity processSingle() {
        doProcessing.doLongRunningSerialProcess();
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(value = "/concurrent/{name}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity processMany(@PathVariable("name") String name) {
        Integer result = doProcessing.doMultiThreadedProcess(name);
        return ResponseEntity.ok(result);
    }
}
