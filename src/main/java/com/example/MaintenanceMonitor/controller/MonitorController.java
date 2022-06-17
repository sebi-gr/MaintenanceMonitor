package com.example.MaintenanceMonitor.controller;

import com.example.MaintenanceMonitor.resource.Monitor;
import com.example.MaintenanceMonitor.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/maintenanceMode")
public class MonitorController {

    @Autowired
    MonitorService monitorService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Monitor> setMonitor(@RequestParam boolean state, @RequestParam(required = false) String message) {
        Monitor resp = monitorService.setMonitor(state, message);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Monitor> getMessages() {
        return new ResponseEntity<>(monitorService.getMonitorData(), HttpStatus.OK);
    }
}
