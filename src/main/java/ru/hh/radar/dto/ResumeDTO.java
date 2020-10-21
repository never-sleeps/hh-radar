package ru.hh.radar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.hh.radar.service.Utils;

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
 *             "age": 25,
 *             "gender": {
 *                 "id": "female",
 *                 "name": "Женский"
 *             },
 *             "salary": null,
 *             "photo": {
 *                 "id": "116290824",
 *                 "small": "https://hhcdn.ru/photo/525949674.jpeg?t=1603399467&h=2vGLE0kDDgl9ewgmUz3gGw",
 *                 "medium": "https://hhcdn.ru/photo/525949675.jpeg?t=1603399467&h=u5ATQ4RGuwd6WHZFLqEgmw",
 *                 "40": "https://hhcdn.ru/photo/525949673.jpeg?t=1603399467&h=OJmzq2jHJiTx6zO62brwMw",
 *                 "100": "https://hhcdn.ru/photo/525949674.jpeg?t=1603399467&h=2vGLE0kDDgl9ewgmUz3gGw",
 *                 "500": "https://hhcdn.ru/photo/525949675.jpeg?t=1603399467&h=u5ATQ4RGuwd6WHZFLqEgmw"
 *             },
 *             "total_experience": {
 *                 "months": 24
 *             },
 *             "certificate": [],
 *             "hidden_fields": [],
 *             "actions": {
 *                 "download": {
 *                     "pdf": {
 *                         "url": "https://hh.ru/api_resume_converter/aa88ec7bff05b482260039ed1f323351454a6f/%D0%9A%D0%BE%D0%BD%D1%8F%D0%BA%D0%B8%D0%BD%D0%B0%20%20%D0%98%D1%80%D0%B8%D0%BD%D0%B0%20%D0%90%D0%BB%D0%B5%D0%BA%D1%81%D0%B0%D0%BD%D0%B4%D1%80%D0%BE%D0%B2%D0%BD%D0%B0.pdf?type=pdf"
 *                     },
 *                     "rtf": {
 *                         "url": "https://hh.ru/api_resume_converter/aa88ec7bff05b482260039ed1f323351454a6f/%D0%9A%D0%BE%D0%BD%D1%8F%D0%BA%D0%B8%D0%BD%D0%B0%20%20%D0%98%D1%80%D0%B8%D0%BD%D0%B0%20%D0%90%D0%BB%D0%B5%D0%BA%D1%81%D0%B0%D0%BD%D0%B4%D1%80%D0%BE%D0%B2%D0%BD%D0%B0.rtf?type=rtf"
 *                     }
 *                 }
 *             },
 *             "url": "https://api.hh.ru/resumes/aa88ec7bff05b482260039ed1f323351454a6f",
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
 *             "education": {
 *                 "level": {
 *                     "id": "higher",
 *                     "name": "Высшее"
 *                 },
 *                 "primary": [
 *                     {
 *                         "name": "Московский государственный технологический университет \"Станкин\", Москва",
 *                         "organization": "Информационные технологии и системы управления",
 *                         "result": null,
 *                         "year": 2019,
 *                         "name_id": "39290",
 *                         "organization_id": null,
 *                         "result_id": null
 *                     }
 *                 ]
 *             },
 *             "experience": [
 *                 {
 *                     "start": "2020-05-01",
 *                     "end": null,
 *                     "company": "ДомКлик",
 *                     "company_id": null,
 *                     "industry": null,
 *                     "industries": [],
 *                     "area": null,
 *                     "company_url": null,
 *                     "employer": null,
 *                     "position": "Java/kotlin разработчик"
 *                 },
 *                 {
 *                     "start": "2019-09-01",
 *                     "end": "2020-05-01",
 *                     "company": "Сбербанк",
 *                     "company_id": "3529",
 *                     "industry": null,
 *                     "industries": [
 *                         {
 *                             "id": "43.647",
 *                             "name": "Банк"
 *                         }
 *                     ],
 *                     "area": {
 *                         "id": "1",
 *                         "name": "Москва",
 *                         "url": "https://api.hh.ru/areas/1"
 *                     },
 *                     "company_url": "http://www.sbrf.ru",
 *                     "employer": {
 *                         "id": "3529",
 *                         "name": "Сбербанк",
 *                         "url": "https://api.hh.ru/employers/3529",
 *                         "alternate_url": "https://hh.ru/employer/3529",
 *                         "logo_urls": {
 *                             "90": "https://hh.ru/employer/logo/3529"
 *                         }
 *                     }
 *                 },
 *                 {
 *                     "start": "2018-11-01",
 *                     "end": "2019-09-01",
 *                     "company": "Кредит Европа Банк",
 *                     "company_id": "1947",
 *                     "industry": null,
 *                     "industries": [
 *                         {
 *                             "id": "43.647",
 *                             "name": "Банк"
 *                         }
 *                     ],
 *                     "area": {
 *                         "id": "1",
 *                         "name": "Москва",
 *                         "url": "https://api.hh.ru/areas/1"
 *                     },
 *                     "company_url": "http://www.crediteurope.ru",
 *                     "employer": {
 *                         "id": "1947",
 *                         "name": "Кредит Европа Банк",
 *                         "url": "https://api.hh.ru/employers/1947",
 *                         "alternate_url": "https://hh.ru/employer/1947",
 *                         "logo_urls": {
 *                             "90": "https://hh.ru/employer/logo/1947"
 *                         }
 *                     }
 *                 }
 *             ],
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
 *             "paid_services": [
 *                 {
 *                     "id": "resume_autoupdating",
 *                     "name": "Автообновление резюме",
 *                     "active": false
 *                 },
 *                 {
 *                     "id": "resume_marked",
 *                     "name": "Яркое резюме",
 *                     "active": false
 *                 }
 *             ],
 *             "blocked": false,
 *             "visible": true,
 *             "created": "2018-09-05T06:35:25+0300",
 *             "updated": "2020-09-15T19:11:20+0300",
 *             "similar_vacancies": {
 *                 "url": "https://api.hh.ru/resumes/aa88ec7bff05b482260039ed1f323351454a6f/similar_vacancies",
 *                 "counters": {
 *                     "total": 2832
 *                 }
 *             },
 *             "new_views": 0,
 *             "total_views": 810,
 *             "views_url": "https://api.hh.ru/resumes/aa88ec7bff05b482260039ed1f323351454a6f/views"
 *         }
 */
@Data
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
    private String updatedAt;

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
    private Number salary;

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
    private static class AccessDTO {
        @JsonProperty(value = "type")
        private TypeDTO type;
    }

    @Override
    public String toString() {
        return ((title != null) ? title + ". " : "")
                + ((status != null) ? status.getName() + ", " : "")
                + ((updatedAt != null) ? "обновлено: " + Utils.getFormattingData(updatedAt) : "")
                ;
    }
}

