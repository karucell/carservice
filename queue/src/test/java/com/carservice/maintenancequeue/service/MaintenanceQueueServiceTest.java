package com.carservice.maintenancequeue.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.carservice.maintenancequeue.WebTestConfiguration;
import com.carservice.maintenancequeue.integration.procedures.EstimatedTime;
import com.carservice.maintenancequeue.integration.procedures.ProceduresRESTClient;
import com.carservice.maintenancequeue.repository.MaintenanceEntity;
import com.carservice.maintenancequeue.repository.MaintenanceQueueRepository;
import com.carservice.maintenancequeue.repository.Priority;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = WebTestConfiguration.class)
public class MaintenanceQueueServiceTest {

    @Autowired
    private MaintenanceQueueService maintenanceQueueService;

    @Autowired
    private MaintenanceQueueRepository maintenanceQueueRepository;

    @Autowired
    private ProceduresRESTClient proceduresRESTClient;

    @Rule
    public PactProviderRuleMk2 scheduleMockProvider = new PactProviderRuleMk2(
            "procedures",
            "localhost",
            org.springframework.util.SocketUtils.findAvailableTcpPort(),
            this
    );

    @Before
    public void setup() {
        proceduresRESTClient.setPort(scheduleMockProvider.getPort());
    }

    @Pact(consumer = "maintenancequeue", provider="procedures")
    public RequestResponsePact pactForEstimatedTimeOfProcedure(PactDslWithProvider builder) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        String procedureId = "PROC1234";
        EstimatedTime estimatedTime = new EstimatedTime(procedureId, 2400L);

        return builder
                       .given("Procedure with id="+procedureId+" exists")
                       .uponReceiving("Request for estimated time of maintenance procedure")
                       .method("GET")
                       .path("/procedures/estimatedtime/"+procedureId)
                       .willRespondWith()
                       .status(HttpStatus.OK.value())
                       .headers(headers)
                       .body("{\"procedureId\":\""+procedureId+"\",\"estimatedTime\":2400}")
                       .toPact();
    }

    @Pact(consumer = "maintenancequeue", provider="procedures")
    public RequestResponsePact pactForFailedEstimatedTimeOfProcedure(PactDslWithProvider builder) throws IOException {
        String procedureId = "PROC1234";

        return builder
                       .given("Procedure with id="+procedureId+" does not exist")
                       .uponReceiving("Request for estimated time of maintenance procedure, which ia missing")
                       .method("GET")
                       .path("/procedures/estimatedtime/"+procedureId)
                       .willRespondWith()
                       .status(HttpStatus.BAD_REQUEST.value())
                       .toPact();
    }

    @Test
    @PactVerification(value = "procedures", fragment = "pactForEstimatedTimeOfProcedure")
    public void addMaintenance() {
        // given
        String carId = "car1234";
        String procedureId = "PROC1234";

        // when
        String maintenanceId = maintenanceQueueService.addMaintenance(carId, procedureId);

        // then
        assertNotNull(maintenanceId);
        // and
        MaintenanceEntity stored = maintenanceQueueRepository.findById(maintenanceId).orElse(null);
        assertNotNull(stored);
        assertEquals(carId, stored.getCarId());
        assertEquals(procedureId, stored.getProcedureId());
    }

    @Test
    @PactVerification(value = "procedures", fragment = "pactForFailedEstimatedTimeOfProcedure")
    public void addMaintenance_requestedProcedureIsMissing_shouldAddMaintenanceWithDefaultEstimate() {
        // given
        String carId = "car1234";
        String procedureId = "PROC1234";

        // when
        String maintenanceId = maintenanceQueueService.addMaintenance(carId, procedureId);

        // then
        assertNotNull(maintenanceId);
        // and
        MaintenanceEntity stored = maintenanceQueueRepository.findById(maintenanceId).orElse(null);
        assertNotNull(stored);
        assertEquals(carId, stored.getCarId());
        assertEquals(procedureId, stored.getProcedureId());
        assertEquals(Priority.DEFAULT, stored.getPriority());
    }

    @Ignore
    @Test
    public void startMaintenance() {
    }

    @Ignore
    @Test
    public void completeMaintenance() {
    }

    @Ignore
    @Test
    public void fetchMaintenances() {
    }

}