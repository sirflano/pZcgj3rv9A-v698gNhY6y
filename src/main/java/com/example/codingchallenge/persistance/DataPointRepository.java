package com.example.codingchallenge.persistance;

import com.example.codingchallenge.model.DataPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataPointRepository extends JpaRepository<DataPoint, Integer>, CustomDataPointRepository {
}
