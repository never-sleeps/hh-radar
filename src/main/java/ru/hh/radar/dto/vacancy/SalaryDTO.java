package ru.hh.radar.dto.vacancy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Оклад
 *
 *     "salary": {
 *         "to": null,
 *         "from": 30000,
 *         "currency": "RUR",
 *         "gross": true
 *     },
 */
@Data
public class SalaryDTO {
    /** Нижняя граница вилки оклада */
    @JsonProperty(value = "to")
    private Number to;

    /** Верняя граница вилки оклада */
    @JsonProperty(value = "from")
    private Number from;

    /** Признак того что оклад указан до вычета налогов. В случае если не указано - null. */
    @JsonProperty(value = "currency")
    private String currency;

    /** Идентификатор валюты оклада (справочник currency). */
    @JsonProperty(value = "gross")
    private Boolean gross;

    public String getGross() {
        return (gross) ? "gross": "";
    }

    @Override
    public String toString() {
        if (from == null && to == null) return null;

        String from = (this.from != null) ? this.from.toString() : "...";
        String to = (this.to != null) ? this.to.toString() : "...";
        return from + " - " + to + " " + getCurrency() + " " + getGross();
    }
}
