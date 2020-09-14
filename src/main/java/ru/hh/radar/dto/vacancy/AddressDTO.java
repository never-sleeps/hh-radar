package ru.hh.radar.dto.vacancy;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressDTO {

    private String city;

    private String street;

    private String buiding;

    @JsonProperty(value = "metro_stations")
    private MetroStation metroStation;


    private static class MetroStation{
        @JsonProperty(value = "station_name")
        private String stationName;

        @JsonProperty(value = "line_name")
        private String lineName;
    }
}
