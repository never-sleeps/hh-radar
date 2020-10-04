package ru.hh.radar.dto.vacancy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Тип вакансии. Элемент справочника vacancy_type.
 *
 *     "type": {
 *         "id": "open",
 *         "name": "Открытая"
 *     },
 */
@Data
public class TypeDTO {
    /** Название типа вакансии (Открытая/Закрытая)*/
    @JsonProperty(value = "name")
    private String name;
}
