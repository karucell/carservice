package com.carservice.maintenanceprocedures.resource;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.carservice.maintenanceprocedures.service.MaintenanceProcedureService;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("maintenanceprocedures")
@PactBroker(
        host = "192.168.99.100",
        port = "8080",
        protocol = "http"
)
@Slf4j
class MaintenanceProcedureControllerTest {

    @MockBean
    MaintenanceProcedureService maintenanceProcedureService;

    private int port;

    @LocalServerPort
    void localServerPort(int port) {
        this.port = port;

    }

    @BeforeEach
    void setupTestTarget(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", this.port, "/"));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State({"MaintenanceProcedure with id=PROC1234 exists"})
    public void stateMaintenanceProcedureWithIdExists() {
        String procedureId = "PROC1234";
        Long estimatedTime = 2400L;
        when(maintenanceProcedureService.getTotalTimeEstimation(procedureId)).thenReturn(estimatedTime);
    }

    @State({"MaintenanceProcedure with id=PROC1234 does not exist"})
    public void stateMaintenanceProcedureWithIdDoesNotExist() {
        String procedureId = "PROC1234";
        Long estimatedTime = null;
        when(maintenanceProcedureService.getTotalTimeEstimation(procedureId)).thenReturn(estimatedTime);
    }

}