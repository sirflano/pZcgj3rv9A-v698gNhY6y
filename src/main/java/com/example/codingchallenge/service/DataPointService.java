package com.example.codingchallenge.service;

import com.example.codingchallenge.model.DataPoint;
import com.example.codingchallenge.model.Query;
import com.example.codingchallenge.model.QueryResult;
import com.example.codingchallenge.persistance.DataPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataPointService {

    @Autowired
    DataPointRepository dataPointRepository;

    public DataPoint save(DataPoint dataPoint) { return dataPointRepository.save(dataPoint);}

    public QueryResult query(Query query) {
        return dataPointRepository.executeQuery(query);
    }
}
