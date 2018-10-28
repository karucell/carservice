package com.carservice.queue.integration.procedures;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProceduresRESTClient {

    private int port;

    public void setPort(int port) {
        this.port = port;
    }

    public Optional<Long> getEstimatedTimeForMaintenance(String procedureId) {
        RestTemplate restTemplate = new RestTemplateBuilder().build();

        String url = String.format("http://%s:%s/procedures/estimatedtime/%s", "localhost", port, procedureId);

        try {
            ResponseEntity<EstimatedTime> response = restTemplate.getForEntity(url, EstimatedTime.class);
            return Stream.of(response)
                    .filter(r -> r.getStatusCode().is2xxSuccessful())
                    .filter(r -> r.getBody() != null)
                    .map(r -> r.getBody().getEstimatedTime())
                    .findFirst();
        } catch (Exception e) {
            log.error("Failed to get estimation from maintenance procedures service", e.getMessage());
        }
        return Optional.empty();
    }

}
