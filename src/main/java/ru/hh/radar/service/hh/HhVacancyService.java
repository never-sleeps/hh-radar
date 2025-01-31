package ru.hh.radar.service.hh;

import ru.hh.radar.dto.ResumeDTO;
import ru.hh.radar.dto.VacancyDTO;
import ru.hh.radar.model.entity.SearchParameters;
import ru.hh.radar.model.entity.User;

import java.util.List;

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
     * @param parameters параметры поиска пользователя
     * @return список вакансий
     */
    List<VacancyDTO> getVacancies(SearchParameters parameters);

    /**
     * Поиск по вакансиям, похожим на резюме
     * https://github.com/hhru/api/blob/master/docs/resumes.md#поиск-по-вакансиям-похожим-на-резюме
     * @param resume резюме пользователя
     * @param user пользователь
     * @return список вакансий
     */
    List<VacancyDTO> getSimilarVacancies(ResumeDTO resume, User user);
}
