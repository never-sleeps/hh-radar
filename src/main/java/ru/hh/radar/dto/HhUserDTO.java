package ru.hh.radar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Информация о пользователе hh: https://api.hh.ru/me
 *
 * {
 *     "auth_type": "applicant",
 *     "is_applicant": true,
 *     "is_employer": false,
 *     "is_admin": false,
 *     "is_application": false,
 *     "id": "72400096",
 *     "is_anonymous": false,
 *     "email": "s-irina-alexandrovna@yandex.ru",
 *     "first_name": "Ирина",
 *     "middle_name": null,
 *     "last_name": "Конякина",
 *     "resumes_url": "https://api.hh.ru/resumes/mine",
 *     "negotiations_url": "https://api.hh.ru/negotiations",
 *     "is_in_search": false,
 *     "mid_name": null,
 *     "employer": null,
 *     "personal_manager": null,
 *     "manager": null,
 *     "phone": "79175390611",
 *     "counters": {
 *         "new_resume_views": 0,
 *         "unread_negotiations": 0,
 *         "resumes_count": 2
 *     }
 * }
 */
@Data
public class HhUserDTO {
    @JsonProperty(value = "first_name")
    private String firstName;

    @JsonProperty(value = "middle_name")
    private String middleName;

    @JsonProperty(value = "last_name")
    private String lastName;

    @Override
    public String toString() {
        return (firstName != null) ? firstName : "";
    }
}
