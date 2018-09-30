package com.carservice.maintenancequeue.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.carservice.maintenancequeue.WebTestConfiguration;
import com.carservice.maintenancequeue.integration.maintenanceprocedures.EstimatedTime;
import com.carservice.maintenancequeue.integration.maintenanceprocedures.MaintenanceProceduresRESTClient;
import com.carservice.maintenancequeue.repository.MaintenanceEntity;
import com.carservice.maintenancequeue.repository.MaintenanceQueueRepository;

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
    private MaintenanceProceduresRESTClient maintenanceProceduresRESTClient;

    @Rule
    public PactProviderRuleMk2 scheduleMockProvider = new PactProviderRuleMk2(
            "maintenanceprocedures",
            "localhost",
            org.springframework.util.SocketUtils.findAvailableTcpPort(),
            this
    );

    @Before
    public void setup() {
        maintenanceProceduresRESTClient.setPort(scheduleMockProvider.getPort());
    }

    @Test
    @PactVerification(value = "maintenanceprocedures", fragment = "pactForEstimatedTimeOfProcedure")
    public void addMaintenance() {
        // given
        String carId = "car-1234";
        String procedureId = "PROC-1234";

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

    @Pact(consumer = "maintenancequeue", provider="maintenanceprocedures")
    public RequestResponsePact pactForEstimatedTimeOfProcedure(PactDslWithProvider builder) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        String procedureId = "PROC-1234";
        EstimatedTime estimatedTime = new EstimatedTime(procedureId, 2400L);

        return builder
                       .given("MaintenanceProcedure exists")
                       .uponReceiving("Request for estimated time of maintenance procedure")
                       .method("GET")
                       //.path("/maintenanceprocedures/estimatedtime/{procedureId}")
                       .path("/maintenanceprocedures/estimatedtime/"+procedureId)
                       .willRespondWith()
                       .status(200)
                       .headers(headers)
                       .body("{\"procedureId\":\""+procedureId+"\",\"estimatedTime\":2400}")
                       .toPact();
    }

    @Test
    public void startMaintenance() {
    }

    @Test
    public void completeMaintenance() {
    }

    @Test
    public void fetchMaintenances() {
    }
}