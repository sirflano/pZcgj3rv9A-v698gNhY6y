package com.example.codingchallenge.model;

import com.example.codingchallenge.dto.DateRangeDTO;

import java.util.List;

public class Query {

    private QueryTypeEnum sensorSelection;

    private QueryTypeEnum fieldsSelection;

    private StatisticTypeEnum statisticType;

    private List<Sensor> sensors;

    private List<String> fields;

    private DateRange dateRange;

    public Query() {}

    public Query(QueryTypeEnum sensorSelection, QueryTypeEnum fieldsSelection, StatisticTypeEnum statisticType, List<Sensor> sensors, List<String> fields, DateRange dateRange) {
        this.sensorSelection = sensorSelection;
        this.fieldsSelection = fieldsSelection;
        this.statisticType = statisticType;
        this.sensors = sensors;
        this.fields = fields;
        this.dateRange = dateRange;
    }

    public QueryTypeEnum getSensorSelection() {
        return sensorSelection;
    }

    public void setSensorSelection(QueryTypeEnum sensorSelection) {
        this.sensorSelection = sensorSelection;
    }

    public QueryTypeEnum getFieldsSelection() {
        return fieldsSelection;
    }

    public void setFieldsSelection(QueryTypeEnum fieldsSelection) {
        this.fieldsSelection = fieldsSelection;
    }

    public StatisticTypeEnum getStatisticType() {
        return statisticType;
    }

    public void setStatisticType(StatisticTypeEnum statisticType) {
        this.statisticType = statisticType;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }
}
