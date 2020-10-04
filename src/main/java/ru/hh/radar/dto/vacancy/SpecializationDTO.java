package ru.hh.radar.dto.vacancy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Специализации. Элементы справочника specializations
 *
 *         {
 *             "profarea_id": "4",
 *             "profarea_name": "Административный персонал",
 *             "id": "4.264",
 *             "name": "Секретарь"
 *         }
 */
@Data
public class SpecializationDTO {
    /** Название профессиональной области, в которую входит специализация */
    @JsonProperty(value = "profarea_name")
    private String profareaName;

    /** Название специализации */
    @JsonProperty(value = "name")
    private String name;
}
