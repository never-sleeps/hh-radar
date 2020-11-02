package ru.hh.radar.service.hh;

import ru.hh.radar.dto.ResumeDTO;
import ru.hh.radar.dto.VacancyDTO;
import ru.hh.radar.model.entity.User;

import java.util.List;

public interface HhSimilarVacancyService {
    /**
     * Поиск по вакансиям, похожим на резюме
     * https://github.com/hhru/api/blob/master/docs/resumes.md#поиск-по-вакансиям-похожим-на-резюме
     * @param resume резюме пользователя
     * @param user пользователь
     * @return список вакансий
     */
    List<VacancyDTO> getSimilarVacancies(ResumeDTO resume, User user);
}
