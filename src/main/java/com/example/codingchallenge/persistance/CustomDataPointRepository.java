package com.example.codingchallenge.persistance;

import com.example.codingchallenge.model.Query;
import com.example.codingchallenge.model.QueryResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

public interface CustomDataPointRepository {
    QueryResult executeQuery(Query query);
}
