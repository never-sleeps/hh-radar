package ru.hh.radar.service.hh;

import ru.hh.radar.dto.ResumeDTO;
import ru.hh.radar.dto.ResumeStatusDTO;
import ru.hh.radar.model.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Сервис для работы с Резюме соискателя
 * https://github.com/hhru/api/blob/master/docs/resumes.md
 */
public interface HhResumeService {

    /**
     * Список резюме авторизованного пользователя
     * https://github.com/hhru/api/blob/master/docs/resumes.md#список-резюме-авторизованного-пользователя
     * @param user пользователь
     * @return объект со списком резюме
     */
    List<ResumeDTO> getAllResume(User user);

    Map<ResumeDTO, ResumeStatusDTO> getAllResumeInfo(User user);

    /**
     * Просмотр резюме
     * https://github.com/hhru/api/blob/master/docs/resumes.md#просмотр-резюме
     * @param resumeId идентификатор резюме
     * @param user пользователь
     * @return резюме
     */
    ResumeDTO getResume(String resumeId, User user);

    /**
     * Информация о статусе резюме и готовности резюме к публикации
     * https://github.com/hhru/api/blob/master/docs/resumes.md#status-and-publication
     * @param user пользователь
     * @return объект с информацией о статусе резюме
     */
    ResumeStatusDTO getStatusResume(String resumeId, User user);

    /**
     * Публикация резюме
     * https://github.com/hhru/api/blob/master/docs/resumes.md#публикация-резюме
     * @param resumeId id
     * @param user пользователь
     * @return В случае успешной публикации вернётся true
     */
    boolean publishResume(String resumeId, User user);
}
