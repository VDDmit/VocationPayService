package ru.vddmit.calculator.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.vddmit.calculator.api.CalendarificClient;
import ru.vddmit.calculator.model.calendarific.CalendarificResponse;
import ru.vddmit.calculator.service.HolidayService;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class HolidayServiceImpl implements HolidayService {

    final CalendarificClient calendarificClient;
    @Value("${calendarific.api-key}")
    String apiKey;

    @Override
    public Set<LocalDate> getPublicHolidays(String countryCode, int year) {
        try {
            CalendarificResponse response = calendarificClient.getHolidays(apiKey, countryCode, year, "national");
            return response.getResponse().getHolidays().stream()
                    .map(h -> LocalDate.parse(h.getDate().getIso()))
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            log.error("Failed to fetch holidays from Calendarific API for country={} and year={}. Reason: {}",
                    countryCode, year, e.getMessage(), e);
            return Collections.emptySet();
        }
    }
}
