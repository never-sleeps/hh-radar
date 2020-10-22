package ru.hh.radar.dto.vacancy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Адрес вакансии
 *
 *  "address": {
 *      "city": "Москва",
 *      "street": "улица Годовикова",
 *      "building": "9с10",
 *      "metro_stations": [
 *          {
 *              "station_name": "Алексеевская",
 *              "line_name": "Калужско-Рижская",
 *          }
 *      ]
 *  }
 */
@Data
public class AddressDTO {
    /** Город */
    @JsonProperty(value = "city")
    private String city;

    /** Улица */
    @JsonProperty(value = "street")
    private String street;

    /** Номер дома */
    @JsonProperty(value = "buiding")
    private String buiding;

    @JsonProperty(value = "metro_stations")
    private List<MetroStation> metroStations;

    public String getMetroStations() {
        StringBuilder str = new StringBuilder();
        for (MetroStation m : metroStations) {
            str.append(m.toString());
        }
        String rezult = str.toString().trim();
        return (rezult.length() < 3 ) ? "" : rezult;
    }

    private static class MetroStation{
        /** Название станции метро */
        @JsonProperty(value = "station_name")
        private String stationName;

        /** Название линии метро, на которой находится станция */
        @JsonProperty(value = "line_name")
        private String lineName;

        @Override
        public String toString() {
            return ((stationName != null) ? "метро " + stationName : "")
                    + ((lineName != null) ? " (линия " + lineName + ")" : "");
        }
    }

    @Override
    public String toString() {
        return ((city != null) ? city + ", " : "")
                + ((street != null) ? street + " " : "")
                + ((buiding != null) ? buiding + " " : "")
                + getMetroStations();
    }
}
