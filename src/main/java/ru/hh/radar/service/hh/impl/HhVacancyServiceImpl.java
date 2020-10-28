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
import ru.hh.radar.dto.VacanciesResultsDTO;
import ru.hh.radar.dto.VacancyDTO;
import ru.hh.radar.model.SearchParametersType;
import ru.hh.radar.model.entity.SearchParameters;
import ru.hh.radar.service.hh.HhVacancyService;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class HhVacancyServiceImpl implements HhVacancyService {

    @Value("${headhunter.api.url}")
    private String url;

    private final RestTemplate restTemplate = new RestTemplate();

    @Cacheable("vacancy")
    @Override
    public VacancyDTO getVacancy(Long id) {
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .path("/vacancies/{id}")
                .buildAndExpand(id)
                .toUri();
        log.info("search vacancy URI: " + uri);

        RequestEntity<?> request = new RequestEntity<>(HttpMethod.GET, uri);
        ResponseEntity<VacancyDTO> response = restTemplate.exchange(request, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    @Override
    public VacanciesResultsDTO getVacancies(SearchParameters parameters) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .path("/vacancies");
        URI uri = applySearchParameters(uriComponentsBuilder, toParametersMap(parameters))
                .build()
                .toUri();
        log.info("search vacancies URI: " + uri);

        RequestEntity<?> request = new RequestEntity<>(HttpMethod.GET, uri);
        ResponseEntity<VacanciesResultsDTO> response = restTemplate.exchange(request, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    private UriComponentsBuilder applySearchParameters(
            UriComponentsBuilder uriComponentsBuilder,
            Map<SearchParametersType, String> searchParameters
    ) {
        for(Map.Entry<SearchParametersType, String> param : searchParameters.entrySet()) {
            if(param.getValue() == null) continue;
            uriComponentsBuilder = uriComponentsBuilder
                    .queryParam(
                            param.getKey().name().toLowerCase(),
                            param.getValue()
                    );
        }
        return uriComponentsBuilder;
    }

    private Map<SearchParametersType, String> toParametersMap(SearchParameters parameters) {
        Map<SearchParametersType, String> map = new HashMap<>();
        map.put(SearchParametersType.AREA, parameters.getArea());
        map.put(SearchParametersType.SPECIALIZATION, parameters.getSpecialization());
        map.put(SearchParametersType.TEXT, parameters.getText());
        map.put(SearchParametersType.EXPERIENCE, parameters.getExperience());
        map.put(SearchParametersType.EMPLOYMENT, parameters.getEmployment());
        map.put(SearchParametersType.SCHEDULE, parameters.getSchedule());
        map.put(SearchParametersType.ORDER_BY, parameters.getOrderBy());
        map.put(SearchParametersType.PAGE, parameters.getPage().toString());
        map.put(SearchParametersType.PER_PAGE, parameters.getPerPage().toString());
        map.put(SearchParametersType.ORDER, parameters.getOrder());
        return map;
    }
}
