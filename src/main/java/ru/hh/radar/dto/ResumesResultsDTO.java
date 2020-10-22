package ru.hh.radar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Результат получения списка вакансий пользователя
 *
 * {
 *     "items": [ ... ],
 *     "found": 2,
 *     "pages": 1,
 *     "per_page": 2,
 *     "page": 0
 * }
 */
@Data
public class ResumesResultsDTO {
    @JsonProperty(value = "items")
    private List<ResumeDTO> items;

    @JsonProperty(value = "found")
    private Long found;

    public Long getFound() {
        return (found != null) ? found : 0;
    }
}
