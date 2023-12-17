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
        String selectQueryPiece = "SELECT ";
        if(query.getFields() == null || query.getFields().isEmpty() || query.getFieldsSelection().equals(QueryTypeEnum.ALL)) {
            //With more time I'd extract these to a fields enum so they could be reused in different parts of the application
            selectFields.add("temperature");
            selectFields.add("humidity");
            selectFields.add("windSpeed");
        } else {
            for(String field : query.getFields()) {
                selectFields.add(field);
            }
        }
        //Creates a db query that selects the fields we want to return and sets the statistic we want this info returned in
        for(int i = 0; i < selectFields.size(); i++) {
            selectQueryPiece+=query.getStatisticType().toString()+"(d."+selectFields.get(i)+")";
            if(i<selectFields.size()-1) {
                selectQueryPiece+=", ";
            } else {
                selectQueryPiece+=" ";
            }
        }
        selectQueryPiece+="FROM DataPoint d ";

        String whereQueryPiece = "";
        List<Object[]> results = new ArrayList();

        //creates a DB query depending on weather we need to select by date range / latest per sensor / specific sensors
        if(query.getSensorSelection().equals(QueryTypeEnum.ALL)) {
            if(query.getDateRange() == null) {
                whereQueryPiece ="WHERE (d.sensor, d.date) IN (SELECT p.sensor, MAX(p.date) FROM DataPoint p GROUP BY p.sensor)";
                results = entityManager.createQuery(selectQueryPiece + whereQueryPiece).getResultList();
            } else {
                whereQueryPiece = "WHERE d.date BETWEEN :startDate AND :endDate";
                results =  entityManager.createQuery(selectQueryPiece + whereQueryPiece).setParameter("startDate", query.getDateRange().getStartDate()).setParameter("endDate", query.getDateRange().getEndDate()).getResultList();
            }
        } else {
            if(query.getDateRange() == null) {
                whereQueryPiece = "WHERE d.sensor IN :sensorIds AND (d.sensor, d.date) IN (SELECT p.sensor, MAX(p.date) FROM DataPoint p GROUP BY p.sensor)";
                results =  entityManager.createQuery(selectQueryPiece + whereQueryPiece).setParameter("sensorIds", query.getSensors()).getResultList();
            } else {
                whereQueryPiece = "WHERE d.sensor IN :sensorIds AND d.date BETWEEN :startDate AND :endDate";
                results =  entityManager.createQuery(selectQueryPiece + whereQueryPiece).setParameter("startDate", query.getDateRange().getStartDate()).setParameter("endDate", query.getDateRange().getEndDate()).getResultList();
            }
        }
        return getQueryResult(selectFields, results);
    }

    private static QueryResult getQueryResult(List<String> selectFields, List<Object[]> results) {
        QueryResult toReturn = new QueryResult();

        for(int i = 0; i< selectFields.size(); i++) {
            switch (selectFields.get(i)){
                case "humidity":
                    toReturn.setHumidity((Double) results.get(0)[i]);
                    break;
                case "temperature":
                    toReturn.setTemperature((Double) results.get(0)[i]);
                    break;
                case "windSpeed":
                    toReturn.setWindSpeed((Double) results.get(0)[i]);
                    break;
            }
        }
        return toReturn;
    }
}
