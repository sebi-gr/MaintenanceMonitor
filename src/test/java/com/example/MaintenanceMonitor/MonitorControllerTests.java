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
    private MonitorService monitorService;

    private Monitor monitor;

    @BeforeEach
    private void setUp () {
        this.monitor = new Monitor();
    }


    //---------------UNIT TESTS------------------
    @Test
    public void contextLoadsController() throws Exception {
        assertThat(monitorController).isNotNull();
    }

    @Test
    public void testSetMessage() {
        monitor.setMessage("Test");
        assertThat(monitor.getMessage()).isEqualTo("Test");
    }

    @Test
    public void testOriginStatus() {
        assertTrue(monitor.isStatus());
    }

    @Test
    public void testSetStatus() {
        monitor.setStatus(false);
        assertFalse(monitor.isStatus());
    }

    //--------------INTEGRATION TESTS---------------
    @Test
    public void testResetEndpoint () {
        Mockito.when(monitorService.resetMonitor()).thenReturn(new Monitor(true, "", new LocalDateTime(2022, 6,17, 12, 0)));

        ResponseEntity<Monitor> responseEntity = monitorController.resetMonitor();

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertTrue(responseEntity.getBody().isStatus());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo("");
        assertThat(responseEntity.getBody().getTimestamp()).isEqualTo(new LocalDateTime(2022, 6,17, 12, 0));
    }

    @Test
    public void testGetMessagesEndpoint () {
        Mockito.when(monitorService.getMonitorData()).thenReturn(new Monitor(true, null, new LocalDateTime(2022, 6,17, 12, 0)));

        ResponseEntity<Monitor> responseEntity = monitorController.getMessages();

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertTrue(responseEntity.getBody().isStatus());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(null);
        assertThat(responseEntity.getBody().getTimestamp()).isEqualTo(new LocalDateTime(2022, 6,17, 12, 0));
    }

}
