package com.example.codingchallenge.model;

import jakarta.annotation.Nullable;

public class QueryResult {

    @Nullable
    private Double temperature;
    @Nullable
    private Double humidity;
    @Nullable
    private Double windSpeed;

    public QueryResult() {}

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
}
