package ru.hh.radar.service.hh.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.hh.radar.dto.TypeDTO;
import ru.hh.radar.service.hh.HhSpecializationsService;

import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class HhSpecializationsServiceImpl implements HhSpecializationsService {

    @Value("${headhunter.api.url}")
    private String url;

    private final RestTemplate restTemplate = new RestTemplate();

    @Cacheable("specializations")
    @Override
    public List<TypeDTO> getSpecializations() {
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .path("/specializations/")
                .build()
                .toUri();
        log.info("search specializations URI: " + uri);

        RequestEntity<?> request = new RequestEntity<>(HttpMethod.GET, uri);
        ResponseEntity<List<TypeDTO>> response = restTemplate.exchange(request, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }
}
