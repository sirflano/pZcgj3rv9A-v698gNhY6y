package com.example.codingchallenge.persistance;

import com.example.codingchallenge.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Parameter;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.verify;

@SpringBootTest
public class CustomDataPointRepositoryImplTest {

    @Autowired
    private CustomDataPointRepositoryImpl customDataPointRepository;
    @Mock
    EntityManager entityManager;

    //With more time I'd have included test for non happy paths here however for now I have covered these in the controller tests
    @Test
    void testExecuteQueryAllSensorsAllFieldsNoDateAVG() {

        Query query = new Query();
        query.setSensorSelection(QueryTypeEnum.ALL);
        query.setFieldsSelection(QueryTypeEnum.ALL);
        query.setStatisticType(StatisticTypeEnum.AVG);

        customDataPointRepository.setEntityManager(entityManager);
        jakarta.persistence.Query mockQuery = Mockito.mock(jakarta.persistence.Query.class);

        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(mockQuery);
        Double[] results = new Double[3];
        results[0] = 1d;
        results[1] = 2d;
        results[2] = 3d;
        List<Double[]> resultsArray = new ArrayList<>();
        resultsArray.add(results);
        Mockito.when(mockQuery.getResultList()).thenReturn(resultsArray);

        QueryResult result = customDataPointRepository.executeQuery(query);
        verify(entityManager).createQuery(captor.capture());
        String dbString = captor.getValue();
        assert(dbString.equals("SELECT AVG(d.temperature), AVG(d.humidity), AVG(d.windSpeed) FROM DataPoint d WHERE (d.sensor, d.date) IN (SELECT p.sensor, MAX(p.date) FROM DataPoint p GROUP BY p.sensor)"));

        assert(result.getTemperature().equals(1d));
        assert(result.getHumidity().equals(2d));
        assert(result.getWindSpeed().equals(3d));
    }

    @Test
    void testExecuteQueryOneSensorAllFieldsNoDate() {

        Query query = new Query();
        query.setSensorSelection(QueryTypeEnum.ONE);
        query.setFieldsSelection(QueryTypeEnum.ALL);
        query.setStatisticType(StatisticTypeEnum.AVG);
        Sensor sensor = new Sensor();
        sensor.setId(1);
        query.setSensors(List.of(sensor));

        customDataPointRepository.setEntityManager(entityManager);
        jakarta.persistence.Query mockQuery = Mockito.mock(jakarta.persistence.Query.class);

        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter(Mockito.anyString(), Mockito.any())).thenReturn(mockQuery);
        Double[] results = new Double[3];
        results[0] = 1d;
        results[1] = 2d;
        results[2] = 3d;
        List<Double[]> resultsArray = new ArrayList<>();
        resultsArray.add(results);
        Mockito.when(mockQuery.getResultList()).thenReturn(resultsArray);

        QueryResult result = customDataPointRepository.executeQuery(query);
        verify(entityManager).createQuery(captor.capture());
        String dbString = captor.getValue();
        assert(dbString.equals("SELECT AVG(d.temperature), AVG(d.humidity), AVG(d.windSpeed) FROM DataPoint d WHERE d.sensor IN :sensorIds AND (d.sensor, d.date) IN (SELECT p.sensor, MAX(p.date) FROM DataPoint p GROUP BY p.sensor)"));

        assert(result.getTemperature().equals(1d));
        assert(result.getHumidity().equals(2d));
        assert(result.getWindSpeed().equals(3d));
    }

    @Test
    void testExecuteQueryAllSensorsOneFieldNoDate() {

        Query query = new Query();
        query.setSensorSelection(QueryTypeEnum.ALL);
        query.setFieldsSelection(QueryTypeEnum.ONE);
        query.setFields(List.of("humidity"));
        query.setStatisticType(StatisticTypeEnum.AVG);

        customDataPointRepository.setEntityManager(entityManager);
        jakarta.persistence.Query mockQuery = Mockito.mock(jakarta.persistence.Query.class);

        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(mockQuery);
        Double[] results = new Double[3];
        results[0] = 1d;
        List<Double[]> resultsArray = new ArrayList<>();
        resultsArray.add(results);
        Mockito.when(mockQuery.getResultList()).thenReturn(resultsArray);

        QueryResult result = customDataPointRepository.executeQuery(query);
        verify(entityManager).createQuery(captor.capture());
        String dbString = captor.getValue();
        assert(dbString.equals("SELECT AVG(d.humidity) FROM DataPoint d WHERE (d.sensor, d.date) IN (SELECT p.sensor, MAX(p.date) FROM DataPoint p GROUP BY p.sensor)"));
        assert(result.getTemperature()==null);
        assert(result.getHumidity().equals(1d));
        assert(result.getTemperature()==null);
    }

    @Test
    void testExecuteQueryAllSensorsAllFieldsDateRange() throws ParseException {

        Query query = new Query();
        query.setSensorSelection(QueryTypeEnum.ALL);
        query.setFieldsSelection(QueryTypeEnum.ALL);
        query.setStatisticType(StatisticTypeEnum.AVG);
        DateRange dateRange = new DateRange();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        dateRange.setStartDate(formatter.parse("15-Dec-2023"));
        dateRange.setEndDate(formatter.parse("17-Dec-2023"));
        query.setDateRange(dateRange);

        customDataPointRepository.setEntityManager(entityManager);
        jakarta.persistence.Query mockQuery = Mockito.mock(jakarta.persistence.Query.class);

        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter(Mockito.anyString(), Mockito.any())).thenReturn(mockQuery);
        Double[] results = new Double[3];
        results[0] = 1d;
        results[1] = 2d;
        results[2] = 3d;
        List<Double[]> resultsArray = new ArrayList<>();
        resultsArray.add(results);
        Mockito.when(mockQuery.getResultList()).thenReturn(resultsArray);

        QueryResult result = customDataPointRepository.executeQuery(query);
        verify(entityManager).createQuery(captor.capture());
        String dbString = captor.getValue();
        System.out.println(dbString);
        assert(dbString.equals("SELECT AVG(d.temperature), AVG(d.humidity), AVG(d.windSpeed) FROM DataPoint d WHERE d.date BETWEEN :startDate AND :endDate"));

        assert(result.getTemperature().equals(1d));
        assert(result.getHumidity().equals(2d));
        assert(result.getWindSpeed().equals(3d));
    }
}
