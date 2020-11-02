package ru.hh.radar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 *
 *
 * [
 *     {
 *         "id": "113",
 *         "parent_id": null,
 *         "name": "Россия",
 *         "areas": [
 *             {
 *                 "id": "1620",
 *                 "name": "Республика Марий Эл"
 *             },
 *             ...
 *         ]
 *     },
 *     ...
 * ]
 */
@Data
public class CountryDTO{
    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "areas")
    private List<TypeDTO> areas;
}
