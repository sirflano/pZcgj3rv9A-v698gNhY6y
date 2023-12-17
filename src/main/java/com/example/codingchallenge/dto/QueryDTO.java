package com.example.codingchallenge.dto;

import com.example.codingchallenge.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class QueryDTO {

    private QueryTypeEnum sensorSelection;

    private QueryTypeEnum fieldsSelection;

    private StatisticTypeEnum statisticType;

    private List<Integer> sensorIds;

    private List<String> fields;

    private DateRangeDTO dateRangeDTO;

    public QueryDTO() {}

    public QueryDTO(QueryTypeEnum sensorSelection, QueryTypeEnum fieldsSelection, StatisticTypeEnum statisticType, List<Integer> sensorIds, List<String> fields, DateRangeDTO dateRangeDTO) {
        this.sensorSelection = sensorSelection;
        this.fieldsSelection = fieldsSelection;
        this.statisticType = statisticType;
        this.sensorIds = sensorIds;
        this.fields = fields;
        this.dateRangeDTO = dateRangeDTO;
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

    public List<Integer> getSensorIds() {
        return sensorIds;
    }

    public void setSensorIds(List<Integer> sensorIds) {
        this.sensorIds = sensorIds;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public DateRangeDTO getDateRangeDTO() {
        return dateRangeDTO;
    }

    public void setDateRangeDTO(DateRangeDTO dateRangeDTO) {
        this.dateRangeDTO = dateRangeDTO;
    }

    @Component
    public static class Mapper {
        public static Query toQuery(QueryDTO queryDTO) {
            List<Sensor> sensors = new ArrayList<>();
            if(queryDTO.getSensorIds()!=null && !queryDTO.getSensorIds().isEmpty()) {
                for(Integer id: queryDTO.getSensorIds()) {
                    sensors.add(new Sensor(id));
                }
            }
            return new Query(queryDTO.getSensorSelection(), queryDTO.getFieldsSelection(), queryDTO.getStatisticType(), sensors, queryDTO.getFields(), DateRangeDTO.Mapper.toDateRange(queryDTO.getDateRangeDTO()));
        }
    }
}
