package ru.hh.radar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 *
 * {
 *     "blocked" : false,
 *     "finished" : false,
 *     "status" : {
 *         "id" : "not_published",
 *         "name" : "не опубликовано"
 *     },
 *     "can_publish_or_update": false,
 *     "publish_url": "https://api.hh.ru/resumes/12345678901234567890123456789012abcdef/publish",
 *     "progress": {
 *         "percentage": 42,
 *         "mandatory": [
 *             {
 *                 "id": "citizenship",
 *                 "name": "Гражданство"
 *             },
 *             {
 *                 "id": "language",
 *                 "name": "Язык"
 *             },
 *             {
 *                 "id": "area",
 *                 "name": "Город проживания"
 *             },
 *             {
 *                 "id": "skills",
 *                 "name": "Ключевые навыки"
 *             },
 *             {
 *                 "id": "contact",
 *                 "name": "Контакты"
 *             },
 *             {
 *                 "id": "education",
 *                 "name": "Образование"
 *             },
 *             {
 *                 "id": "specialization",
 *                 "name": "Специализация"
 *             }
 *         ],
 *         "recommended": [
 *             {
 *                 "id": "salary",
 *                 "name": "Заработная плата"
 *             },
 *             {
 *                 "id": "middle_name",
 *                 "name": "Отчество"
 *             },
 *             {
 *                 "id": "work_ticket",
 *                 "name": "Разрешение на работу"
 *             },
 *             {
 *                 "id": "site",
 *                 "name": "Сайт"
 *             },
 *             {
 *                 "id": "recommendation",
 *                 "name": "Рекомендации"
 *             },
 *             {
 *                 "id": "birth_date",
 *                 "name": "Дата рождения"
 *             }
 *         ]
 *     },
 *     "moderation_note": [
 *         {
 *             "id": "bad",
 *             "name": "Резюме составлено небрежно."
 *         },
 *         {
 *             "id": "block_no_education_place_or_date",
 *             "name": "Отсутствуют данные об учебном заведении и дате его окончания.",
 *             "field": "education"
 *         }
 *     ]
 * }
 */
@Data
public class ResumeStatusDTO {

    /** флаг заблокированности резюме */
    @JsonProperty(value = "blocked")
    private Boolean blocked;

    /** флаг о заполненности резюме */
    @JsonProperty(value = "finished")
    private Boolean finished;

    /**
     *     "status" : {
     *         "id" : "not_published",
     *         "name" : "не опубликовано"
     *     },
     */
    @JsonProperty(value = "status")
    private TypeDTO status;

    /** Можно ли опубликовать или обновить данное резюме */
    @JsonProperty(value = "can_publish_or_update")
    private Boolean canPublishOrUpdate;
}
