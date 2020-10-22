package ru.hh.radar.dto.vacancy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Короткое представление работодателя. Описание полей смотрите в информации о работодателе.
 *
 *     "employer": {
 *         "logo_urls": {
 *             "90": "https://hh.ru/employer-logo/289027.png",
 *             "240": "https://hh.ru/employer-logo/289169.png",
 *             "original": "https://hh.ru/file/2352807.png"
 *         },
 *         "name": "HeadHunter",
 *         "url": "https://api.hh.ru/employers/1455",
 *         "alternate_url": "https://hh.ru/employer/1455",
 *         "id": "1455",
 *         "trusted": true,
 *         "blacklisted": false
 *     }
 */
@Data
public class EmployerDTO {
    /** название компании */
    @JsonProperty(value = "name")
    private String name;

    /** url для получения полного описания работодателя */
    @JsonProperty(value = "url")
    private String url;

    /** ссылка на описание работодателя на сайте */
    @JsonProperty(value = "alternate_url")
    private String alternateUrl;
}
