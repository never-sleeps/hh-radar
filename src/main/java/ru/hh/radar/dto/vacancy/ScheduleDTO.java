package ru.hh.radar.dto.vacancy;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * График работы. Элемент справочника schedule
 *
 *     "schedule": {
 *         "id": "fullDay",
 *         "name": "Полный день"
 *     },
 */
@Data
public class ScheduleDTO {
    /** Идентификатор графика работы */
    @JsonProperty(value = "id")
    private String id;

    /** Название графика работы */
    @JsonProperty(value = "name")
    private String name;
}
