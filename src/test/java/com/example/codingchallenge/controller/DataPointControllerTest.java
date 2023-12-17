package com.example.codingchallenge.controller;

import com.example.codingchallenge.dto.DateRangeDTO;
import com.example.codingchallenge.dto.QueryDTO;
import com.example.codingchallenge.model.QueryResult;
import com.example.codingchallenge.model.QueryTypeEnum;
import com.example.codingchallenge.model.StatisticTypeEnum;
import com.example.codingchallenge.service.DataPointService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DataPointController.class)
public class DataPointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DataPointService dataPointService;

    //With more time I'd have included non happy paths for the create call
    @Test
    void testQuery() throws Exception {
        QueryDTO queryDTO = new QueryDTO();
        queryDTO.setFields(List.of("", ""));
        queryDTO.setFieldsSelection(QueryTypeEnum.MANY);
        queryDTO.setSensorIds(List.of(1));
        queryDTO.setSensorSelection(QueryTypeEnum.ONE);
        queryDTO.setStatisticType(StatisticTypeEnum.AVG);
        queryDTO.setDateRangeDTO(new DateRangeDTO(new Date(), new Date()));

        QueryResult queryResult = new QueryResult();
        queryResult.setWindSpeed(10d);
        queryResult.setHumidity(11d);
        queryResult.setTemperature(12d);
        when(dataPointService.query(Mockito.any())).thenReturn(queryResult);

        String jsonInput = "{\n" +
                "    \"sensorSelection\":\"ALL\",\n" +
                "    \"fieldsSelection\":\"ALL\",\n" +
                "    \"statisticType\":\"MIN\",\n" +
                "    \"sensorIds\": [1],\n" +
                "    \"fields\": [\"humidity\", \"windSpeed\"],\n" +
                "    \"dateRangeDTO\": {\n" +
                "        \"startDate\":\"2022-12-17T17:08:06.192+00:00\",\n" +
                "        \"endDate\":\"2023-12-15T17:08:06.192+00:00\"\n" +
                "    }\n" +
                "}";

        MvcResult result = this.mockMvc.perform(get("/query").contentType(MediaType.APPLICATION_JSON)
                .content(jsonInput.getBytes())).andExpect(status().isOk())
                .andReturn();

        assert (result.getResponse().getContentAsString().equals("{\"temperature\":12.0,\"humidity\":11.0,\"windSpeed\":10.0}"));
    }

    @Test
    void testQueryMissingStisticType() throws Exception {
        String jsonInput = "{\n" +
                "    \"sensorSelection\":\"ALL\",\n" +
                "    \"fieldsSelection\":\"ALL\",\n" +
                "    \"sensorIds\": [1],\n" +
                "    \"fields\": [\"humidity\", \"windSpeed\"],\n" +
                "    \"dateRangeDTO\": {\n" +
                "        \"startDate\":\"2022-12-17T17:08:06.192+00:00\",\n" +
                "        \"endDate\":\"2023-12-15T17:08:06.192+00:00\"\n" +
                "    }\n" +
                "}";

        MvcResult result = this.mockMvc.perform(get("/query").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput.getBytes())).andExpect(status().is4xxClientError())
                .andReturn();

    }
    @Test
    void testQueryMissingSensors() throws Exception {
        String jsonInput = "{\n" +
                "    \"fieldsSelection\":\"ALL\",\n" +
                "    \"statisticType\":\"MIN\",\n" +
                "    \"fields\": [\"humidity\", \"windSpeed\"],\n" +
                "    \"dateRangeDTO\": {\n" +
                "        \"startDate\":\"2022-12-17T17:08:06.192+00:00\",\n" +
                "        \"endDate\":\"2023-12-15T17:08:06.192+00:00\"\n" +
                "    }\n" +
                "}";

        MvcResult result = this.mockMvc.perform(get("/query").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput.getBytes())).andExpect(status().is4xxClientError())
                .andReturn();

    }
    @Test
    void testQueryMissingFields() throws Exception {
        String jsonInput = "{\n" +
                "    \"sensorSelection\":\"ALL\",\n" +
                "    \"statisticType\":\"MIN\",\n" +
                "    \"sensorIds\": [1],\n" +
                "    \"dateRangeDTO\": {\n" +
                "        \"startDate\":\"2022-12-17T17:08:06.192+00:00\",\n" +
                "        \"endDate\":\"2023-12-15T17:08:06.192+00:00\"\n" +
                "    }\n" +
                "}";

        MvcResult result = this.mockMvc.perform(get("/query").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput.getBytes())).andExpect(status().is4xxClientError())
                .andReturn();

    }
    @Test
    void testAddDatePoint() throws Exception {
        String jsonInput = "{\n" +
                "    \"sensorId\":1,\n" +
                "\n" +
                "    \"temperature\":15,\n" +
                "\n" +
                "    \"humidity\":15,\n" +
                "\n" +
                "    \"windSpeed\":105.2,\n" +
                "\n" +
                "    \"date\":\"2023-12-17T17:08:06.192+00:00\"\n" +
                "}";

        MvcResult result = this.mockMvc.perform(post("/addDataPoint").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput.getBytes())).andExpect(status().isOk())
                .andReturn();
    }
}
