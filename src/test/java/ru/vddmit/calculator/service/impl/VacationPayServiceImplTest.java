package ru.vddmit.calculator.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.vddmit.calculator.service.HolidayService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class VacationPayServiceImplTest {

    @Mock
    HolidayService holidayService;

    VacationPayServiceImpl vacationPayService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vacationPayService = new VacationPayServiceImpl(holidayService);
    }

    @Test
    void calculateVacationPay_withoutHolidays() {
        BigDecimal averageSalary = new BigDecimal("100000");
        int vacationDays = 14;

        BigDecimal expected = averageSalary.divide(new BigDecimal("29.3"), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(vacationDays))
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal result = vacationPayService.calculateVacationPay(averageSalary, vacationDays, null);

        assertEquals(expected, result);
    }

    @Test
    void calculateVacationPay_withWeekendsAndHolidays() {
        BigDecimal averageSalary = new BigDecimal("90000");

        List<LocalDate> vacationDates = List.of(
                LocalDate.of(2024, 5, 7),  // Вторник
                LocalDate.of(2024, 5, 8),  // Среда
                LocalDate.of(2024, 5, 9),  // Четверг (праздник)
                LocalDate.of(2024, 5, 10), // Пятница
                LocalDate.of(2024, 5, 11), // Суббота (выходной)
                LocalDate.of(2024, 5, 12)  // Воскресенье (выходной)
        );

        when(holidayService.getPublicHolidays(2024)).thenReturn(Set.of(LocalDate.of(2024, 5, 9)));

        // Рабочие дни: 7 мая, 8 мая, 10 мая (3 дня)
        BigDecimal expected = averageSalary.divide(new BigDecimal("29.3"), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(3))
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal result = vacationPayService.calculateVacationPay(averageSalary, 6, vacationDates);

        assertEquals(expected, result);

        verify(holidayService, times(1)).getPublicHolidays(2024);
    }

    @Test
    void calculateVacationPay_allDaysNonPayable() {
        BigDecimal averageSalary = new BigDecimal("75000");

        List<LocalDate> vacationDates = List.of(
                LocalDate.of(2024, 5, 9),  // Праздник
                LocalDate.of(2024, 5, 11), // Суббота
                LocalDate.of(2024, 5, 12)  // Воскресенье
        );

        when(holidayService.getPublicHolidays(2024)).thenReturn(Set.of(LocalDate.of(2024, 5, 9)));

        BigDecimal result = vacationPayService.calculateVacationPay(averageSalary, 3, vacationDates);

        assertEquals(BigDecimal.ZERO.setScale(2), result);

        verify(holidayService, times(1)).getPublicHolidays(2024);
    }
}