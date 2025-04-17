package ru.vddmit.calculator.service;

import java.time.LocalDate;
import java.util.Set;

public interface HolidayService {
    Set<LocalDate> getPublicHolidays(int year);
}
