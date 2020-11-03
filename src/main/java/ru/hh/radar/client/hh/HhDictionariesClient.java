package ru.hh.radar.client.hh;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.hh.radar.dto.CountryDTO;
import ru.hh.radar.dto.TypeDTO;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HhDictionariesClient {

    @Value("${headhunter.api.url}")
    private String url;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<CountryDTO> getCountries() {
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .path("/areas/")
                .build()
                .toUri();

        RequestEntity<?> request = new RequestEntity<>(HttpMethod.GET, uri);
        ResponseEntity<List<CountryDTO>> response = restTemplate.exchange(request, new ParameterizedTypeReference<>() {});
        List<CountryDTO> countries = response.getBody();

        return (countries != null) ? countries : List.of();
    }

    public List<TypeDTO> getSpecializations() {
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .path("/specializations/")
                .build()
                .toUri();

        RequestEntity<?> request = new RequestEntity<>(HttpMethod.GET, uri);
        ResponseEntity<List<TypeDTO>> response = restTemplate.exchange(request, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }
}
