package com.example.MaintenanceMonitor.controller;

import com.example.MaintenanceMonitor.resource.Monitor;
import com.example.MaintenanceMonitor.service.MonitorService;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/monitor")
public class MonitorController {

    @Autowired
    private Monitor monitor;

    @Autowired
    MonitorService monitorService;

    @GetMapping(value = "/reset", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Monitor> resetMonitor() {
        Monitor resp = monitorService.resetMonitor();
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Monitor> getMessages() {
        return new ResponseEntity<>(monitorService.getMonitorData(), HttpStatus.OK);
    }
}
