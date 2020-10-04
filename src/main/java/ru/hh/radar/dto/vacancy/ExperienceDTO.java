package ru.hh.radar.dto.vacancy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Требуемый опыт работы. Элемент справочника experience
 *
 *     "experience": {
 *         "id": "between1And3",
 *         "name": "От 1 года до 3 лет"
 *     },
 */
@Data
public class ExperienceDTO {
    /** Идентификатор требуемого опыта работы */
    @JsonProperty(value = "id")
    private String id;

    /** Название требуемого опыта работы */
    @JsonProperty(value = "name")
    private String name;
}