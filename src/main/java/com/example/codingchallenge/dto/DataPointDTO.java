package com.example.codingchallenge.dto;

import com.example.codingchallenge.model.DataPoint;
import com.example.codingchallenge.model.Sensor;
import org.springframework.stereotype.Component;

import java.util.Date;

public class DataPointDTO {

    private Integer sensorId;

    private Double temperature;

    private Double humidity;

    private Double windSpeed;

    private Date date;

    public DataPointDTO() {}

    public DataPointDTO(Integer sensorId, Double temperature, Double humidity, Double windSpeed, Date date){
        this.sensorId = sensorId;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.date = date;
    }

    public Integer getSensorId() {
        return sensorId;
    }

    public void setSensorId(Integer sensorId) {
        this.sensorId = sensorId;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Component
    public static class Mapper {
        public static DataPoint toDataPoint(DataPointDTO dataPointDTO) {
            Date date = new Date();
            Sensor parent = new Sensor();
            parent.setId(dataPointDTO.getSensorId());
            if(dataPointDTO.getDate() != null) {
                date = dataPointDTO.getDate();
            }
            DataPoint toReturn = new DataPoint(dataPointDTO.getTemperature(), dataPointDTO.getHumidity(), dataPointDTO.getWindSpeed(), date);
            toReturn.setSensor(parent);
            return toReturn;
        }
    }
}
