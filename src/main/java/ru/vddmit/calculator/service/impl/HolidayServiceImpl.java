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

    private static final String COUNTRY_CODE = "RU";

    @Override
    public Set<LocalDate> getPublicHolidays(int year) {
        try {
            CalendarificResponse response = calendarificClient.getHolidays(
                    apiKey,
                    COUNTRY_CODE,
                    year,
                    "national"
            );

            Set<LocalDate> holidays = response.getResponse().getHolidays().stream()
                    .map(h -> LocalDate.parse(h.getDate().getIso()))
                    .collect(Collectors.toSet());

            log.info("Received {} holidays for {} year: {}", holidays.size(), year, holidays);
            return holidays;

        } catch (Exception e) {
            log.error("Failed to fetch holidays for country={} and year={}. Reason: {}",
                    COUNTRY_CODE, year, e.getMessage(), e);
            return Collections.emptySet();
        }
    }
}
