package com.example.codingchallenge.persistance;

import com.example.codingchallenge.model.Query;
import com.example.codingchallenge.model.QueryResult;
import com.example.codingchallenge.model.QueryTypeEnum;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

public class CustomDataPointRepositoryImpl implements CustomDataPointRepository{
    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public QueryResult executeQuery(Query query) {

        List<String> selectFields = new ArrayList<>();
        String selectQuery = "SELECT ";
        if(query.getFields() == null || query.getFields().isEmpty() || query.getFieldsSelection().equals(QueryTypeEnum.ALL)) {
            selectFields.add("temperature");
            selectFields.add("humidity");
            selectFields.add("windSpeed");
        } else {
            for(String field : query.getFields()) {
                selectFields.add(field);
            }
        }
        for(int i = 0; i < selectFields.size(); i++) {
            selectQuery+=query.getStatisticType().toString()+"(d."+selectFields.get(i)+")";
            if(i<selectFields.size()-1) {
                selectQuery+=", ";
            } else {
                selectQuery+=" ";
            }
        }
        selectQuery+="FROM DataPoint d ";
        String dateSelectionQuery = "";
        List<Object[]> results = new ArrayList();

        if(query.getSensorSelection().equals(QueryTypeEnum.ALL)) {
            if(query.getDateRange() == null) {
                dateSelectionQuery ="WHERE (d.sensor, d.date) IN (SELECT p.sensor, MAX(p.date) FROM DataPoint p GROUP BY p.sensor)";
                results = entityManager.createQuery(selectQuery + dateSelectionQuery).getResultList();
            } else {
                dateSelectionQuery = "WHERE d.date BETWEEN :startDate AND :endDate";
                results =  entityManager.createQuery(selectQuery + dateSelectionQuery).setParameter("startDate", query.getDateRange().getStartDate()).setParameter("endDate", query.getDateRange().getEndDate()).getResultList();
            }
        } else {
            if(query.getDateRange() == null) {
                dateSelectionQuery = "WHERE d.sensor IN :sensorIds AND (d.sensor, d.date) IN (SELECT p.sensor, MAX(p.date) FROM DataPoint p GROUP BY p.sensor)";
                results =  entityManager.createQuery(selectQuery + dateSelectionQuery).setParameter("sensorIds", query.getSensors()).getResultList();
            } else {
                dateSelectionQuery = "WHERE d.sensor IN :sensorIds AND d.date BETWEEN :startDate AND :endDate";
                results =  entityManager.createQuery(selectQuery + dateSelectionQuery).setParameter("startDate", query.getDateRange().getStartDate()).setParameter("endDate", query.getDateRange().getEndDate()).getResultList();
            }
        }
        QueryResult toReturn = new QueryResult();

        for(int i = 0; i< selectFields.size(); i++) {
            switch (selectFields.get(i)){
                case "humidity":
                    toReturn.setHumidity((Double)results.get(0)[i]);
                    break;
                case "temperature":
                    toReturn.setTemperature((Double)results.get(0)[i]);
                    break;
                case "windSpeed":
                    toReturn.setWindSpeed((Double)results.get(0)[i]);
                    break;
            }
        }
        return toReturn;
    }
}
