package ru.hh.radar.service.hh;

import ru.hh.radar.dto.SearchParameters;
import ru.hh.radar.dto.VacanciesSearchResultsDTO;
import ru.hh.radar.dto.VacancyDTO;

import java.util.List;

public interface HhVacancyService {

    /**
     * Просмотр вакансии
     * https://github.com/hhru/api/blob/master/docs/vacancies.md#item
     * @param id вакансии
     */
    VacancyDTO getVacancy(Long id);

    /**
     * Поиск по вакансиям
     * https://github.com/hhru/api/blob/master/docs/vacancies.md#search
     * @param searchParameters набор параметров поиска
     * @return список вакансий
     */
    VacanciesSearchResultsDTO getVacancies(SearchParameters searchParameters);
}
