package com.carservice.queue.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.carservice.queue.WebTestConfiguration;
import com.carservice.queue.integration.procedures.EstimatedTime;
import com.carservice.queue.integration.procedures.ProceduresRESTClient;
import com.carservice.queue.repository.Priority;
import com.carservice.queue.repository.QueueEntity;
import com.carservice.queue.repository.QueueRepository;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.model.RequestResponsePact;

@ExtendWith(SpringExtension.class)
@ExtendWith(PactConsumerTestExt.class)
@SpringBootTest
@ContextConfiguration(classes = WebTestConfiguration.class)
public class QueueServiceTest {

    @Autowired
    private QueueService queueService;

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private ProceduresRESTClient proceduresRESTClient;

    @BeforeEach
    void setup(MockServer mockServer) {
        proceduresRESTClient.setPort(mockServer.getPort());
    }

    @Pact(consumer = "queue", provider="procedures")
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

    @Pact(consumer = "queue", provider="procedures")
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
    @PactTestFor(providerName = "procedures", pactMethod = "pactForEstimatedTimeOfProcedure")
    void addMaintenance(MockServer mockServer) {
        // given
        String carId = "car1234";
        String procedureId = "PROC1234";

        // when
        String maintenanceId = queueService.addMaintenance(carId, procedureId);

        // then
        assertNotNull(maintenanceId);
        // and
        QueueEntity stored = queueRepository.findById(maintenanceId).orElse(null);
        assertNotNull(stored);
        assertEquals(carId, stored.getCarId());
        assertEquals(procedureId, stored.getProcedureId());
    }

    @Test
    @PactTestFor(providerName = "procedures", pactMethod = "pactForFailedEstimatedTimeOfProcedure")
    public void addMaintenance_requestedProcedureIsMissing_shouldAddMaintenanceWithDefaultEstimate(MockServer mockServer) {
        // given
        String carId = "car1234";
        String procedureId = "PROC1234";

        // when
        String maintenanceId = queueService.addMaintenance(carId, procedureId);

        // then
        assertNotNull(maintenanceId);
        // and
        QueueEntity stored = queueRepository.findById(maintenanceId).orElse(null);
        assertNotNull(stored);
        assertEquals(carId, stored.getCarId());
        assertEquals(procedureId, stored.getProcedureId());
        assertEquals(Priority.DEFAULT, stored.getPriority());
    }

//    @Test
//    public void startMaintenance() {
//    }

//    @Test
//    public void completeMaintenance() {
//    }

//    @Test
//    public void fetchMaintenances() {
//    }

}