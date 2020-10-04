package ru.hh.radar.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.hh.radar.dto.SearchParameter;
import ru.hh.radar.dto.VacanciesSearchResultsDTO;
import ru.hh.radar.dto.VacancyDTO;
import ru.hh.radar.service.HhVacancyService;

import java.net.URI;
import java.util.List;


@Service
public class HhVacancyServiceImpl implements HhVacancyService {

    private final static Logger log = LoggerFactory.getLogger(HhVacancyServiceImpl.class);

    @Value("${headhunter.api.url}")
    private String url;

    private RestTemplate restTemplate = new RestTemplate();

    @Cacheable("vacancy")
    @Override
    public VacancyDTO getVacancy(Long id) {
        log.info("getVacancy by id = " + id);
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .path("/vacancies/{id}")
                .buildAndExpand(id)
                .toUri();

        RequestEntity requestEntity = new RequestEntity(HttpMethod.GET, uri);
        ResponseEntity<VacancyDTO> responseEntity =
                restTemplate.exchange(requestEntity, new ParameterizedTypeReference<>() {});
        return responseEntity.getBody();
    }

    @Override
    public VacanciesSearchResultsDTO getVacancies(List<SearchParameter> searchParameters) {
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .path("/vacancies")
                .queryParam(searchParameters.get(0).getKey(), searchParameters.get(0).getValue())
                .build()
                .toUri();

        RequestEntity requestEntity = new RequestEntity(HttpMethod.GET, uri);

        ResponseEntity<VacanciesSearchResultsDTO> responseEntity =
                restTemplate.exchange(requestEntity, new ParameterizedTypeReference<>() {});
        return responseEntity.getBody();
    }
}
