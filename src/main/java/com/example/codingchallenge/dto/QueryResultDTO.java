package com.example.codingchallenge.dto;

import com.example.codingchallenge.model.QueryResult;
import org.springframework.stereotype.Component;

public class QueryResultDTO {
    private Double temperature;
    private Double humidity;
    private Double windSpeed;

    public QueryResultDTO() {}

    public QueryResultDTO(Double temperature, Double humidity, Double windSpeed) {
        this.humidity = humidity;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    @Component
    public static class Mapper {
        public static QueryResultDTO toQueryResultDTO(QueryResult queryResult) {
            return new QueryResultDTO(queryResult.getTemperature(), queryResult.getHumidity(), queryResult.getWindSpeed());
        }
    }
}
