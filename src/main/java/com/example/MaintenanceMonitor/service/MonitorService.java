package com.example.MaintenanceMonitor.service;

import com.example.MaintenanceMonitor.resource.Monitor;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MonitorService {

    private Monitor monitor;

    @Autowired
    public MonitorService(Monitor monitor) {
        this.monitor = monitor;
    }

    public Monitor setMonitor (boolean state, String message) {
        Optional.ofNullable(message).orElseThrow(NullPointerException::new);
        monitor.setMessage(message);
        monitor.setStatus(state);
        monitor.setTimestamp(new LocalDateTime());
        return monitor;
    }

    public Monitor getMonitorData () {
        monitor.setTimestamp(new LocalDateTime());
        return monitor;
    }
}
