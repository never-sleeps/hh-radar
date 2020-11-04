package ru.hh.radar.service.common.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.hh.radar.dto.VacanciesResultsDTO;

import java.net.URI;

/**
 * Heroku может отключать неиспользуемые сервисы.
 * Чтобы этого не случилось, пингую api.hh.ru
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PingService {
    @Value("${headhunter.api.url}")
    private String url;
    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedDelay = 1 * 60 * 60 * 1000) // раз в час
    public void pingMe() {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(url)
                    .path("/vacancies")
                    .build()
                    .toUri();
            RequestEntity<?> request = new RequestEntity<>(HttpMethod.GET, uri);
            ResponseEntity<VacanciesResultsDTO> response = restTemplate.exchange(request, new ParameterizedTypeReference<>() {});

            log.info("Ping {}, OK: response code {}", uri.toString(), response.getStatusCodeValue());
        } catch (Exception e) {
            log.error("Ping FAILED");
            e.printStackTrace();
        }
    }
}
