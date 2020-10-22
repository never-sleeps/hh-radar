package ru.hh.radar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Результат поиска вакансий
 *
 * {
 *     "items": [
 *         { ... }
 *     ],
 *     "found": 254603,
 *     "pages": 100,
 *     "per_page": 20,
 *     "page": 0,
 *     "clusters": null,
 *     "arguments": null,
 *     "alternate_url": "https://hh.ru/search/vacancy?enable_snippets=true&experience=noExperience"
 * }
 */
@Data
public class VacanciesResultsDTO {
    @JsonProperty(value = "items")
    private List<VacancyDTO> items;

    @JsonProperty(value = "found")
    private long found;

    @JsonProperty(value = "alternate_url")
    private String alternateUrl;

    @Override
    public String toString() {
        return "VacanciesSearchResultsDTO{" +
                "found=" + found +
                ", alternateUrl='" + alternateUrl + '\'' +
                '}';
    }
}
