package ru.vddmit.calculator.service;

import java.time.LocalDate;
import java.util.Set;

public interface HolidayService {
    Set<LocalDate> getPublicHolidays(String countryCode, int year);
}
