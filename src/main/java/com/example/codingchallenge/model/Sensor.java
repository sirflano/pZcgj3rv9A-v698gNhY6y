package com.example.codingchallenge.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="sensor")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy ="sensor")
    private List<DataPoint> dataPoints;

    public Sensor(){}

    public Sensor(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(List<DataPoint> dataPoints) {
        this.dataPoints = dataPoints;
    }
}
