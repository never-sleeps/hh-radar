package ru.hh.radar.dto.vacancy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Адрес вакансии
 *
 *     "address": {
 *         "city": "Москва",
 *         "street": "улица Годовикова",
 *         "building": "9с10",
 *         "description": "на проходной потребуется паспорт",
 *         "lat": 55.807794,
 *         "lng": 37.638699,
 *         "metro_stations": [
 *             {
 *                 "station_id": "6.8",
 *                 "station_name": "Алексеевская",
 *                 "line_id": "6",
 *                 "line_name": "Калужско-Рижская",
 *                 "lat": 55.807794,
 *                 "lng": 37.638699
 *             }
 *         ]
 *     },
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


    private static class MetroStation{
        /** Название станции метро */
        @JsonProperty(value = "station_name")
        private String stationName;

        /** Название линии метро, на которой находится станция */
        @JsonProperty(value = "line_name")
        private String lineName;
    }
}
