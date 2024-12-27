package com.journals.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherResponse {

    private Current current;

    private Location location;

    @Getter
    @Setter
    public class Current{

        private int temperature;

        @JsonProperty("weather_descriptions")
        private List<String> weatherDescriptions;

        private int feelslike;

    }

    @Getter
    @Setter
    public class Location{

        private String country;

        @JsonProperty("timezone_id")
        private String timezoneId;

        private String localtime;
    }

}
