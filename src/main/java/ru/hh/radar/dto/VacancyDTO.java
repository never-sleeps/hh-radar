package ru.hh.radar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.hh.radar.dto.vacancy.*;
import ru.hh.radar.service.Utils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Вакансия
 *
 * {
 *     "id": "8331228",
 *     "description": "...",
 *     "key_skills": [
 *         {
 *             "name": "Прием посетителей"
 *         },
 *         {
 *             "name": "Первичный документооборот"
 *         }
 *     ],
 *     "schedule": {
 *         "id": "fullDay",
 *         "name": "Полный день"
 *     },
 *     "experience": {
 *         "id": "between1And3",
 *         "name": "От 1 года до 3 лет"
 *     },
 *     "address": {
 *         "city": "Москва",
 *         "street": "улица Годовикова",
 *         "building": "9с10",
 *         "metro_stations": [
 *             {
 *                 "station_name": "Алексеевская",
 *                 "line_name": "Калужско-Рижская"
 *             }
 *         ]
 *     },
 *     "alternate_url": "https://hh.ru/vacancy/8331228",
 *     "apply_alternate_url": "https://hh.ru/applicant/vacancy_response?vacancyId=8331228",
 *     "employment": {
 *         "name": "Полная занятость"
 *     },
 *     "salary": {
 *         "to": null,
 *         "from": 30000,
 *         "currency": "RUR",
 *         "gross": true
 *     },
 *     "archived": false,
 *     "name": "Секретарь",
 *     "published_at": "2013-07-08T16:17:21+0400",
 *     "employer": {
 *         "name": "HeadHunter",
 *         "url": "https://api.hh.ru/employers/1455",
 *         "alternate_url": "https://hh.ru/employer/1455"
 *     },
 *     "snippet": {
 *          "requirement": "Образование не ниже среднего - специального. Пользователь ПК. Готовность к обучению. Желание работать и зарабатывать достойно. Коммуникабельность, ответственность, активная жизненная позиция...",
 *          "responsibility": "Ведение переговоров. Заключение договоров. Организация сделок. Проведение операций по продаже, покупке: Вторичной недвижимости. Загородной недвижимости. Первичной недвижимости. Коммерческой недвижимости. "
 *     },
 *     "response_letter_required": true,
 *     "type": {
 *         "name": "Открытая"
 *     },
 *     "has_test": true,
 *     "specializations": [
 *         {
 *             "profarea_id": "4",
 *             "profarea_name": "Административный персонал",
 *             "id": "4.264",
 *             "name": "Секретарь"
 *         },
 *         {
 *             "profarea_id": "4",
 *             "profarea_name": "Административный персонал",
 *             "id": "4.181",
 *             "name": "Начальный уровень, Мало опыта"
 *         }
 *     ]
 * }
 */
@Data
public class VacancyDTO {
    /** Идентификатор вакансии */
    @JsonProperty(value = "id")
    private String id;

    /** Описание вакансии, содержит html */
    @JsonProperty(value = "description")
    private String description;

    /** Информация о ключевых навыках, заявленных в вакансии. Список может быть пустым. */
    @JsonProperty(value = "key_skills")
    private List<SkillDTO> skills;

    /**
     * График работы. Элемент справочника schedule
     *
     *     "schedule": {
     *         "id": "fullDay",
     *         "name": "Полный день"
     *     },
     */
    @JsonProperty(value = "schedule")
    private TypeDTO schedule;

    /**
     * Требуемый опыт работы. Элемент справочника experience
     *
     *     "experience": {
     *         "id": "between1And3",
     *         "name": "От 1 года до 3 лет"
     *     },
     */
    @JsonProperty(value = "experience")
    private TypeDTO experience;

    /** Адрес вакансии */
    @JsonProperty(value = "address")
    private AddressDTO address;

    /** Ссылка на представление вакансии на сайте */
    @JsonProperty(value = "alternate_url")
    private String alternateUrl;

    /** Ссылка на отклик на вакансию на сайте */
    @JsonProperty(value = "apply_alternate_url")
    private String applyAlternateUrl;

    /**
     * Тип занятости. Элемент справочника employment.
     *
     *     "employment": {
     *         "id": "full",
     *         "name": "Полная занятость"
     *     },
     */
    @JsonProperty(value = "employment")
    private TypeDTO employment;

    /** Оклад */
    @JsonProperty(value = "salary")
    private SalaryDTO salary;

    /** Находится ли данная вакансия в архиве */
    @JsonProperty(value = "archived")
    private boolean archived;

    /** Название вакансии */
    @JsonProperty(value = "name")
    private String name;

    /** Дата и время публикации вакансии */
    @JsonProperty(value = "published_at")
    private LocalDateTime publishedAt;

    /** Короткое представление работодателя. Описание полей смотрите в информации о работодателе. */
    @JsonProperty(value = "employer")
    private EmployerDTO employer;

    /** Требования и обязанности */
    @JsonProperty(value = "snippet")
    private SnippetDTO snippet;

    /** Обязательно ли заполнять сообщение при отклике на вакансию */
    @JsonProperty(value = "response_letter_required")
    private boolean responseLetterRequired;

    /** Тип вакансии. Элемент справочника vacancy_type. */
    @JsonProperty(value = "type")
    private TypeDTO type;

    /** Информация о наличии прикрепленного тестового задании к вакансии. В случае присутствия теста - true.*/
    @JsonProperty(value = "has_test")
    private Boolean hasTest;

    /** Специализации. Элементы справочника specializations */
    @JsonProperty(value = "specializations")
    private List<SpecializationDTO> specializations;

    public String getDescription() {
        if(description == null || description.length() <= 400) {
            return description;
        }
        return description.substring(0, 400) + "...";
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = Utils.getLocalDateTime(publishedAt);
    }

    @Override
    public String toString() {
        return "\uD83D\uDCCC " + name + " ("+employer.getName()+")" + "\n"
                + "Дата публикации: \uD83D\uDD58" + Utils.getFormattingData(publishedAt) + "\n"
                + ((experience != null) ? "Требуемый опыт: " + experience.getName() + "\n" : "")
                + ((schedule != null) ? schedule.getName() + "\n" : "")
                + ((employment != null) ? employment.getName() + "\n"  : "")
                + ((salary != null && salary.toString() != null) ? salary.toString() + "\n": "")
                + ((address != null) ? "Адрес: " + address.toString() + "\n" : "")
                + "\n"
                + ((snippet != null) ? snippet.toString() + "\n" : "")
                + ((getDescription() != null) ? "Описание вакансии: " + Utils.htmlToText(getDescription()) + "\n" : "")
                + getAlternateUrl()
                ;
    }
}