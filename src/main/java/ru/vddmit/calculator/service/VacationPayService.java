package ru.vddmit.calculator.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface VacationPayService {
    BigDecimal calculateVacationPay(BigDecimal averageSalary, Integer vacationDays, List<LocalDate> vacationDates);
}
