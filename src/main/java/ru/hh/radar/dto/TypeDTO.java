package ru.hh.radar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Тип
 *
 *     "type": {
 *         "id": "open",
 *         "name": "Открытая"
 *     },
 *      "type": {
 *          "id": "no_one",
 *          "name": "не видно никому"
 *      }
 */
@Data
public class TypeDTO {
    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "name")
    private String name;
}
