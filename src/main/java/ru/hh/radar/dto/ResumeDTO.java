package ru.hh.radar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hh.radar.utils.Utils;

import java.time.LocalDateTime;

/**
 *  Резюме авторизованного пользователя
 *
 *         {
 *             "last_name": "Конякина ",
 *             "first_name": "Ирина",
 *             "middle_name": "Александровна",
 *             "title": "Java-разработчик",
 *             "created_at": "2018-09-05T06:35:25+0300",
 *             "updated_at": "2020-09-15T19:11:20+0300",
 *             "area": {
 *                 "id": "1",
 *                 "name": "Москва",
 *                 "url": "https://api.hh.ru/areas/1"
 *             },
 *             "salary": null,
 *             "alternate_url": "https://hh.ru/resume/aa88ec7bff05b482260039ed1f323351454a6f",
 *             "id": "aa88ec7bff05b482260039ed1f323351454a6f",
 *             "download": {
 *                 "pdf": {
 *                     "url": "https://hh.ru/api_resume_converter/aa88ec7bff05b482260039ed1f323351454a6f/%D0%9A%D0%BE%D0%BD%D1%8F%D0%BA%D0%B8%D0%BD%D0%B0%20%20%D0%98%D1%80%D0%B8%D0%BD%D0%B0%20%D0%90%D0%BB%D0%B5%D0%BA%D1%81%D0%B0%D0%BD%D0%B4%D1%80%D0%BE%D0%B2%D0%BD%D0%B0.pdf?type=pdf"
 *                 },
 *                 "rtf": {
 *                     "url": "https://hh.ru/api_resume_converter/aa88ec7bff05b482260039ed1f323351454a6f/%D0%9A%D0%BE%D0%BD%D1%8F%D0%BA%D0%B8%D0%BD%D0%B0%20%20%D0%98%D1%80%D0%B8%D0%BD%D0%B0%20%D0%90%D0%BB%D0%B5%D0%BA%D1%81%D0%B0%D0%BD%D0%B4%D1%80%D0%BE%D0%B2%D0%BD%D0%B0.rtf?type=rtf"
 *                 }
 *             },
 *             "finished": true,
 *             "status": {
 *                 "id": "published",
 *                 "name": "опубликовано"
 *             },
 *             "access": {
 *                 "type": {
 *                     "id": "no_one",
 *                     "name": "не видно никому"
 *                 }
 *             },
 *             "blocked": false,
 *             "visible": true,
 *             "updated": "2020-09-15T19:11:20+0300",
 *         }
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeDTO {

    /** Идентификатор резюме */
    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "last_name")
    private String lastName;

    @JsonProperty(value = "first_name")
    private String firstName;

    @JsonProperty(value = "middle_name")
    private String middleName;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "updated_at")
    private LocalDateTime updatedAt;

    /**
     *      "area": {
     *          "id": "1",
     *          "name": "Москва",
     *          "url": "https://api.hh.ru/areas/1"
     *      }
     */
    @JsonProperty(value = "area")
    private TypeDTO area;

    @JsonProperty(value = "salary")
    private SalaryDTO salary;

    /** Ссылка на вакансию */
    @JsonProperty(value = "alternate_url")
    private String alternateUrl;

    /** флаг о заполненности резюме */
    @JsonProperty(value = "finished")
    private Boolean finished;

    /**
     *     "status": {
     *         "id": "published",
     *         "name": "опубликовано"
     *     },
     */
    @JsonProperty(value = "status")
    private TypeDTO status;

    @JsonProperty(value = "access")
    private AccessDTO access;

    /** флаг заблокированности резюме */
    @JsonProperty(value = "blocked")
    private Boolean blocked;

    @JsonProperty(value = "updated")
    private String updated;


    /**
     *     "access": {
     *         "type": {
     *             "id": "no_one",
     *             "name": "не видно никому"
     *         }
     *     },
     */
    @Data
    private static class AccessDTO {
        @JsonProperty(value = "type")
        private TypeDTO type;
    }

    /**
     *    "salary": {
     *        "amount": 1,
     *        "currency": "RUR"
     *     }
     */
    @Data
    private static class SalaryDTO {
        @JsonProperty(value = "amount")
        private String amount;

        @JsonProperty(value = "currency")
        private String currency;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = Utils.getLocalDateTime(updatedAt);;
    }

    public boolean isCanBeUpdatedByTimePeriod(int timeBetweenPublishingInHours) {
        return updatedAt.plusHours(timeBetweenPublishingInHours).isBefore(LocalDateTime.now().plusHours(3));
    }

    public boolean isPublished() {
        return status.getId().equals("published");
    }

    @Override
    public String toString() {
        return ((title != null) ? title + ". " : "unnamed. ")
                + "Желаемая зарплата: " + ((salary != null) ? salary.getAmount() + " " + salary.getCurrency().toLowerCase() : "не указано") + "\n"
                + "Статус: " + ((status != null) ? status.getName() + "" : "") + "\n"
                + "Видимость резюме: " + ((access != null) && (access.getType() != null) ? access.getType().getName() : "") + "\n"
                + "Обновлено: " + ((updatedAt != null) ? Utils.getFormattingData(updatedAt) + "\n" : "")
                + getAlternateUrl()
                ;
    }

    public String toShortString() {
        return ((title != null) ? title + ". " : "unnamed. ")
                + ((updatedAt != null) ? Utils.getFormattingData(updatedAt) : "")
                ;
    }

    public ResumeDTO(String id) {
        this.id = id;
    }
}

