package ru.hh.radar.dto.vacancy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Информация о ключевых навыках, заявленных в вакансии. Список может быть пустым
 *
 *     "key_skills": [
 *         {
 *             "name": "Прием посетителей"
 *         }
 *     ]
 */
@Data
public class SkillDTO {
    /** название ключевого навыка */
    @JsonProperty(value = "name")
    private String name;
}
