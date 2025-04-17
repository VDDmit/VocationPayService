package ru.vddmit.calculator.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vddmit.calculator.model.VacationRequest;
import ru.vddmit.calculator.model.VacationResponse;
import ru.vddmit.calculator.service.VacationPayService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/calculate")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class VacationPayController {

    VacationPayService vacationPayService;

    @GetMapping
    public ResponseEntity<VacationResponse> calculate(@ModelAttribute VacationRequest request) {
        BigDecimal result = vacationPayService.calculateVacationPay(
                request.getAverageSalary(),
                request.getVacationDays(),
                request.getVacationDates()
        );

        return ResponseEntity.ok(
                new VacationResponse(result, "Расчёт выполнен успешно")
        );
    }
}
