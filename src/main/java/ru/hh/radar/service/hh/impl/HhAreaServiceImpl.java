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
import ru.hh.radar.dto.CountryDTO;
import ru.hh.radar.dto.TypeDTO;
import ru.hh.radar.service.hh.HhAreaService;

import java.net.URI;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HhAreaServiceImpl implements HhAreaService {

    @Value("${headhunter.api.url}")
    private String url;

    private final RestTemplate restTemplate = new RestTemplate();

    @Cacheable("areas")
    @Override
    public List<TypeDTO> getRussiaAreas() {
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .path("/areas/")
                .build()
                .toUri();
        log.info("search areas URI: " + uri);

        RequestEntity<?> request = new RequestEntity<>(HttpMethod.GET, uri);
        ResponseEntity<List<CountryDTO>> response = restTemplate.exchange(request, new ParameterizedTypeReference<>() {});
        List<CountryDTO> countries = response.getBody();

        if(countries == null) return List.of();
        return getSortingRussianAreas(countries);
    }

    private List<TypeDTO> getSortingRussianAreas(List<CountryDTO> countries) {
        for (CountryDTO country : countries) {
            if (country.getName().equals("Россия")) {
                List<TypeDTO> russianAreas = country.getAreas();
                Comparator<TypeDTO> compareByName = new Comparator<>() {
                    @Override
                    public int compare(TypeDTO o1, TypeDTO o2) {
//                        if (o1.getName().equals("Москва")) return -1;
//                        if (o1.getName().equals("Санкт-Петербург")) return -1;
                        return o1.getName().compareTo(o2.getName());
                    }
                };
                russianAreas.sort(compareByName);
                return russianAreas;
            }
        }
        log.error("Россия not found");
        return List.of();
    }
}
