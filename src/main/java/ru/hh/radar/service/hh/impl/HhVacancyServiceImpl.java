package ru.hh.radar.service.hh.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.hh.radar.client.hh.HhVacancyClient;
import ru.hh.radar.dto.ResumeDTO;
import ru.hh.radar.dto.VacanciesResultsDTO;
import ru.hh.radar.dto.VacancyDTO;
import ru.hh.radar.model.entity.SearchParameters;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.hh.HhVacancyService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class HhVacancyServiceImpl implements HhVacancyService {

    private final HhVacancyClient hhVacancyClient;

    @Cacheable("vacancy")
    @Override
    public VacancyDTO getVacancy(Long id) {
        return hhVacancyClient.getVacancy(id);
    }

    @Override
    public List<VacancyDTO> getVacancies(SearchParameters parameters) {
        VacanciesResultsDTO results = hhVacancyClient.getVacancies(parameters);
        log.info("found " + results.getFound() + " vacancies");
        return results.getItems();
    }

    @Override
    public List<VacancyDTO> getSimilarVacancies(ResumeDTO resume, User user) {
        SearchParameters enrichedParameters = enrichSearchParameters(user.getSearchParameters(), resume);
        user.setSearchParameters(enrichedParameters);
        VacanciesResultsDTO results = hhVacancyClient.getSimilarVacancies(resume, user);
        log.info(results.getFound() + " found similar vacancies");
        return results.getItems();
    }

    private SearchParameters enrichSearchParameters(SearchParameters parameters, ResumeDTO resume) {
        parameters.setText(resume.getTitle());
        parameters.setArea(resume.getArea().getId());
        return parameters;
    }
}
