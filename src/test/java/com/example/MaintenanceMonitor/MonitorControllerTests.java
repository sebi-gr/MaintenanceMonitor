package com.example.MaintenanceMonitor;

import com.example.MaintenanceMonitor.controller.MonitorController;
import com.example.MaintenanceMonitor.resource.Monitor;
import com.example.MaintenanceMonitor.service.MonitorService;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MonitorControllerTests {

    @InjectMocks
    private MonitorController monitorController;

    @Mock
    private MonitorService monitorServiceMock;

    private Monitor monitor;
    private MonitorService monitorService;

    @BeforeEach
    private void setUp () {
        this.monitor = new Monitor();
        monitorService = new MonitorService(this.monitor);
    }


    //---------------UNIT TESTS------------------
    @Test
    public void contextLoadsController() throws Exception {
        assertThat(monitorController).isNotNull();
    }

    @Test
    public void testOriginStatus() {
        assertTrue(monitor.isStatus());
        assertThat(monitor.getMessage()).isEqualTo("-");
    }

    @Test
    public void testSetMonitor () {
        monitorService.setMonitor(false, "down");
        assertThat(monitor.getMessage()).isEqualTo("down");
        assertFalse(monitor.isStatus());
        assertThat(monitor.getTimestamp()).isLessThanOrEqualTo(new LocalDateTime());
    }

    @Test
    public void testSetMonitor_null () {
        monitorService.setMonitor(false, null);
        assertThat(monitor.getMessage()).isEqualTo("-");
        assertFalse(monitor.isStatus());
        assertThat(monitor.getTimestamp()).isLessThanOrEqualTo(new LocalDateTime());
    }
    
    @Test
    public void testGetMonitorData () {
        monitorService.getMonitorData();
        assertThat(monitor.getMessage()).isEqualTo("-");
        assertTrue(monitor.isStatus());
        assertThat(monitor.getTimestamp()).isLessThanOrEqualTo(new LocalDateTime());
    }

    //--------------INTEGRATION TESTS---------------
    @Test
    public void testResetEndpoint_downtime () {
        Mockito.when(monitorServiceMock.setMonitor(Mockito.anyBoolean(), Mockito.anyString())).thenReturn(new Monitor(false, "No Connection", new LocalDateTime(2022, 6,17, 12, 0)));

        ResponseEntity<Monitor> responseEntity = monitorController.setMonitor(false, "No Connection");

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertFalse(responseEntity.getBody().isStatus());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo("No Connection");
        assertThat(responseEntity.getBody().getTimestamp()).isLessThanOrEqualTo(new LocalDateTime(2022, 6,17, 12, 0));
    }

    @Test
    public void testResetEndpoint_uptime () {
        Mockito.when(monitorServiceMock.setMonitor(Mockito.anyBoolean(), Mockito.anyString())).thenReturn(new Monitor(true, "-", new LocalDateTime(2022, 6,17, 12, 0)));

        ResponseEntity<Monitor> responseEntity = monitorController.setMonitor(true, "");

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertTrue(responseEntity.getBody().isStatus());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo("-");
        assertThat(responseEntity.getBody().getTimestamp()).isLessThanOrEqualTo(new LocalDateTime(2022, 6,17, 12, 0));
    }

    @Test
    public void testGetMessagesEndpoint () {
        Mockito.when(monitorServiceMock.getMonitorData()).thenReturn(new Monitor(true, "-", new LocalDateTime(2022, 6,17, 12, 0)));

        ResponseEntity<Monitor> responseEntity = monitorController.getMessages();

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertTrue(responseEntity.getBody().isStatus());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo("-");
        assertThat(responseEntity.getBody().getTimestamp()).isLessThanOrEqualTo(new LocalDateTime(2022, 6,17, 12, 0));
    }

}
