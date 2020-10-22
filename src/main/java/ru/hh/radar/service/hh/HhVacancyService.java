package ru.hh.radar.service.hh;

import ru.hh.radar.dto.VacanciesResultsDTO;
import ru.hh.radar.dto.VacancyDTO;
import ru.hh.radar.model.SearchParameters;

/**
 * Сервис для работы с вакансиями
 * https://github.com/hhru/api/blob/master/docs/vacancies.md
 */
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
    VacanciesResultsDTO getVacancies(SearchParameters searchParameters);
}
