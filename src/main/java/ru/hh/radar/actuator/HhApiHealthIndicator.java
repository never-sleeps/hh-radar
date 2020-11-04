package ru.hh.radar.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.hh.radar.dto.VacanciesResultsDTO;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class HhApiHealthIndicator implements HealthIndicator {

    @Value("${headhunter.api.url}")
    private String url;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Health health() {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(url)
                    .path("/vacancies")
                    .build()
                    .toUri();
            RequestEntity<?> request = new RequestEntity<>(HttpMethod.GET, uri);
            ResponseEntity<VacanciesResultsDTO> response = restTemplate.exchange(request, new ParameterizedTypeReference<>() {});

            int statusCodeValue = response.getStatusCodeValue();
            if (statusCodeValue >= 200 && statusCodeValue < 300) {
                return Health.up().build();
            } else {
                return Health.down().withDetail("api.hh.ru Status Code", statusCodeValue).build();
            }
        } catch (Exception e) {
            return Health.down(e).build();
        }
    }
}
