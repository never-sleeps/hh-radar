package ru.hh.radar.dto.vacancy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 *
 *  "snippet": {
 *       "requirement": "Образование не ниже среднего - специального. Пользователь ПК. Готовность к обучению. Желание работать и зарабатывать достойно. Коммуникабельность, ответственность, активная жизненная позиция...",
 *       "responsibility": "Ведение переговоров. Заключение договоров. Организация сделок. Проведение операций по продаже, покупке: Вторичной недвижимости. Загородной недвижимости. Первичной недвижимости. Коммерческой недвижимости. "
 *  }
 */
@Data
public class SnippetDTO {
    @JsonProperty(value = "requirement")
    private String requirement;

    @JsonProperty(value = "responsibility")
    private String responsibility;

    @Override
    public String toString() {
        return ((requirement != null) ? "⚠️Требования: " + requirement + "\n" : "")
                + ((responsibility != null) ? "❕Обязанности:" + responsibility : "");
    }
}
