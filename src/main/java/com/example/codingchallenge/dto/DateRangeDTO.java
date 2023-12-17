package com.example.codingchallenge.dto;

import com.example.codingchallenge.model.DateRange;
import org.springframework.stereotype.Component;

import java.util.Date;

public class DateRangeDTO {

    private Date startDate;
    private Date endDate;

    public DateRangeDTO() {}

    public  DateRangeDTO(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Component
    public static class Mapper {
        public static DateRange toDateRange(DateRangeDTO dateRangeDTO) {
            if(dateRangeDTO==null){
                return null;
            }
            return new DateRange(dateRangeDTO.getStartDate(), dateRangeDTO.getEndDate());
        }
    }
}
