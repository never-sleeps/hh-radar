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
    private boolean gross;

    @Override
    public String toString() {
        if (from == null && to == null) return null;

        String from = (this.from != null) ? this.from.toString() : "...";
        String to = (this.to != null) ? this.to.toString() : "...";
        String currency = (this.currency != null) ? this.currency.toLowerCase() : "rub";
        String gross = (this.gross) ? "gross" : "net";
        return from + " - " + to + " " + currency + " " + gross;
    }
}
