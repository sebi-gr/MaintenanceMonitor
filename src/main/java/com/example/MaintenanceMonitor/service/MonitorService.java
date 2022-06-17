package com.example.MaintenanceMonitor.service;

import com.example.MaintenanceMonitor.resource.Monitor;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitorService {

    @Autowired
    private Monitor monitor;

    public Monitor resetMonitor () {
        monitor.setMessage("");
        monitor.setStatus(true);
        monitor.setTimestamp(new LocalDateTime());
        return monitor;
    }
}
