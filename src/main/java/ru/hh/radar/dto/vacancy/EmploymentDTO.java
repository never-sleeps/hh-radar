package ru.hh.radar.dto.vacancy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Тип занятости. Элемент справочника employment.
 *
 *     "employment": {
 *         "id": "full",
 *         "name": "Полная занятость"
 *     },
 */
@Data
public class EmploymentDTO {
    @JsonProperty(value = "name")
    private String name;
}
