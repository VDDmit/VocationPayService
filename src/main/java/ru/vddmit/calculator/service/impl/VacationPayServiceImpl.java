package ru.vddmit.calculator.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.vddmit.calculator.service.HolidayService;
import ru.vddmit.calculator.service.VacationPayService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class VacationPayServiceImpl implements VacationPayService {

    static BigDecimal AVERAGE_DAYS_IN_MONTH = new BigDecimal("29.3");

    HolidayService holidayService;

    @Override
    public BigDecimal calculateVacationPay(BigDecimal averageSalary, Integer vacationDays, List<LocalDate> vacationDates) {
        int payableDays = vacationDays;

        if (vacationDates != null && !vacationDates.isEmpty()) {
            int year = vacationDates.get(0).getYear(); // Предполагаем, что даты в одном году
            Set<LocalDate> holidays = holidayService.getPublicHolidays(year);

            payableDays = (int) vacationDates.stream()
                    .filter(date -> isWorkingDay(date, holidays))
                    .count();
        }

        BigDecimal dailyRate = averageSalary
                .divide(AVERAGE_DAYS_IN_MONTH, 2, RoundingMode.HALF_UP);

        return dailyRate
                .multiply(BigDecimal.valueOf(payableDays))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private boolean isWorkingDay(LocalDate date, Set<LocalDate> holidays) {
        DayOfWeek day = date.getDayOfWeek();
        return day != DayOfWeek.SATURDAY &&
                day != DayOfWeek.SUNDAY &&
                !holidays.contains(date);
    }
}
