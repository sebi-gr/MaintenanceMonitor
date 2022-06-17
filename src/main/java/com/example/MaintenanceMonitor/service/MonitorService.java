package com.example.MaintenanceMonitor.service;

import com.example.MaintenanceMonitor.resource.Monitor;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitorService {

    @Autowired
    private Monitor monitor;

    public Monitor setMonitor (boolean state, String message) {
        if (message == null) {
            monitor.setMessage("-");
        } else {
            monitor.setMessage(message);
        }
        monitor.setStatus(state);
        monitor.setTimestamp(new LocalDateTime());
        return monitor;
    }

    public Monitor getMonitorData () {
        monitor.setTimestamp(new LocalDateTime());
        return monitor;
    }
}
