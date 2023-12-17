package com.example.codingchallenge.dto;

import com.example.codingchallenge.model.DataPoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class DataPointDTOTest {

    //Had I more time I'd have unit tested the rest of the DTO classes, in a similar vein
    @Test
    void MapperTest() {
        DataPointDTO dataPointDTO = new DataPointDTO();
        Date dateInTest = new Date();
        dataPointDTO.setDate(dateInTest);
        dataPointDTO.setHumidity(10d);
        dataPointDTO.setSensorId(1);
        dataPointDTO.setTemperature(11d);
        dataPointDTO.setWindSpeed(12d);

        DataPoint result = DataPointDTO.Mapper.toDataPoint(dataPointDTO);
        assert(result.getDate().equals(dateInTest));
        assert(result.getHumidity()==10d);
        assert(result.getSensor().getId()==1);
        assert(result.getTemperature()==11d);
        assert(result.getWindSpeed()==12d);
    }

}
