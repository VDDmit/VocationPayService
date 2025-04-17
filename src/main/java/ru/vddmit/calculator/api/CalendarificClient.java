package ru.vddmit.calculator.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vddmit.calculator.model.calendarific.CalendarificResponse;

@FeignClient(name = "calendarificClient", url = "https://calendarific.com/api/v2")
public interface CalendarificClient {
    @GetMapping("/holidays")
    CalendarificResponse getHolidays(
            @RequestParam("api_key") String apiKey,
            @RequestParam("country") String country,
            @RequestParam("year") int year,
            @RequestParam(value = "type", defaultValue = "national") String type
    );
}
