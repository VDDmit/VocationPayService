package ru.vddmit.calculator.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)

public class VacationRequest {
    BigDecimal averageSalary;
    Integer vacationDays;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    List<LocalDate> vacationDates;
}
