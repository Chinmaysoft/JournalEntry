package com.journals.journalApp.service;

import com.journals.journalApp.api.response.WeatherResponse;
import com.journals.journalApp.cache.AppCache;
import com.journals.journalApp.constants.PlaceHolders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    @Value("${weather.api.key}")
    private String apikey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String city) {
        String finalApi = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(PlaceHolders.CITY, city).replace(PlaceHolders.API_KEY, apikey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.POST, null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }


}
