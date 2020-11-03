package ru.hh.radar.service.hh.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.hh.radar.client.hh.HhDictionariesClient;
import ru.hh.radar.dto.CountryDTO;
import ru.hh.radar.dto.TypeDTO;
import ru.hh.radar.service.hh.HhDictionaryService;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HhDictionaryServiceImpl implements HhDictionaryService {

    private final HhDictionariesClient hhDictionariesClient;

    @Cacheable("areas")
    @Override
    public List<TypeDTO> getRussiaAreas() {
        List<CountryDTO> countries = hhDictionariesClient.getCountries();
        for (CountryDTO country : countries) {
            if (country.getName().equals("Россия")) {
                List<TypeDTO> russianAreas = country.getAreas();
                Comparator<TypeDTO> compareByName = new Comparator<>() {
                    @Override
                    public int compare(TypeDTO o1, TypeDTO o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                };
                russianAreas.sort(compareByName);
                log.info("Russian areas found: " + russianAreas.size());
                return russianAreas;
            }
        }
        log.error("Россия not found in country list");
        return List.of();
    }

    @Cacheable("specializations")
    @Override
    public List<TypeDTO> getSpecializations() {
        List<TypeDTO> specializations = hhDictionariesClient.getSpecializations();
        log.info("Specializations found: " + specializations.size());
        return specializations;
    }
}
