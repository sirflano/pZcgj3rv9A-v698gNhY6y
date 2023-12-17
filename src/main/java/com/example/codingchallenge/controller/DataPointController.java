package com.example.codingchallenge.controller;

import com.example.codingchallenge.dto.DataPointDTO;
import com.example.codingchallenge.dto.QueryDTO;
import com.example.codingchallenge.dto.QueryResultDTO;
import com.example.codingchallenge.model.QueryTypeEnum;
import com.example.codingchallenge.service.DataPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.MalformedParametersException;

@RestController
public class DataPointController {

    @Autowired
    DataPointService dataPointService;

    @GetMapping(value = "/query")
    public @ResponseBody QueryResultDTO queryData(@RequestBody QueryDTO queryDTO) {
        if((null == queryDTO.getFieldsSelection() || !queryDTO.getFieldsSelection().equals(QueryTypeEnum.ALL)) && (queryDTO.getFields() == null || queryDTO.getFields().isEmpty())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please either select fields or set to all fields");
        }
        if((null == queryDTO.getSensorSelection() || !queryDTO.getSensorSelection().equals(QueryTypeEnum.ALL)) && (queryDTO.getSensorIds() == null || queryDTO.getSensorIds().isEmpty())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please either select sensors or set to all sensors");
        }
        if(null == queryDTO.getStatisticType()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please select statistic type");
        }
        //Fields are loaded as enums so an error is automatically thrown if the user requests a field that isn't available

        return QueryResultDTO.Mapper.toQueryResultDTO(dataPointService.query(QueryDTO.Mapper.toQuery(queryDTO)));
    }

    @PostMapping(value = "/addDataPoint")
    public void addDataPoint(@RequestBody DataPointDTO dataPointDTO) {
        if(null == dataPointDTO.getSensorId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sensor ID not found");
        }
        //The brief didn't specify if the fields should be nullable, were that a requirement I would change the DataPoint object to use Doubles instead of doubles.
        if(null == dataPointDTO.getTemperature()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Temperature not found");
        }
        if(null == dataPointDTO.getHumidity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Humidity not found");
        }
        if(null == dataPointDTO.getWindSpeed()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wind Speed not found");
        }
        dataPointService.save(DataPointDTO.Mapper.toDataPoint(dataPointDTO));
    }
}
