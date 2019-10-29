package com.mg.insuranceapp.rest;

import com.mg.insuranceapp.entities.AdditionalFee;
import com.mg.insuranceapp.entities.TarifPrice;
import com.mg.insuranceapp.enums.InsuranceAdditional;
import com.mg.insuranceapp.enums.InsuranceTarif;
import com.mg.insuranceapp.enums.InsuranceType;
import com.mg.insuranceapp.repos.AdditionalFeeRepository;
import com.mg.insuranceapp.repos.TarifPriceRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mg.insuranceapp.enums.InsuranceType.SHORT_TERM;
import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;
import static java.time.temporal.ChronoUnit.DAYS;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(path = "/calculation", produces = "application/json")
@Validated
public class CalculationController {

    private final Map<InsuranceTarif, BigDecimal> insuranceTarifDoubleMap;
    private final Map<InsuranceAdditional, BigDecimal> insuranceAdditionalDoubleMap;

    public CalculationController(final TarifPriceRepository tarifPriceRepository,
                                 final AdditionalFeeRepository additionalFeeRepository) {
        insuranceTarifDoubleMap = tarifPriceRepository.findAll().stream()
                .collect(Collectors.toMap(t -> InsuranceTarif.valueOf(t.getId()), TarifPrice::getPrice));
        insuranceAdditionalDoubleMap = additionalFeeRepository.findAll().stream()
                .collect(Collectors.toMap(f -> InsuranceAdditional.valueOf(f.getId()), AdditionalFee::getPercentage));
    }

    @RequestMapping(method = GET)
    public Double getCalculation(
            @RequestParam("insuranceType") final InsuranceType type,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate endDate,
            @RequestParam("insuranceTarif") final InsuranceTarif tarif,
            @RequestParam(value = "insuranceAdditional", required = false) final Set<InsuranceAdditional> additionals,
            @RequestParam("peopleAmount") @Min(1) @Max(3) final Integer people) {
        if ( type == SHORT_TERM ) {
            if ( endDate == null )
                throw new IllegalArgumentException("In case of short term insurance end date should be nonempty!");
            else if ( endDate.isBefore(startDate) )
                throw new IllegalArgumentException("startDate should be before endDate");
        }


        long days = 365;
        if ( type == SHORT_TERM ) {
            days = DAYS.between(startDate, endDate) + 1;
        }

        final BigDecimal price = valueOf(people).multiply(valueOf(days)).multiply(getDayPrice(tarif, additionals));
        return price.setScale(2, HALF_UP).doubleValue();
    }

    private BigDecimal getDayPrice(final InsuranceTarif tarif, final Set<InsuranceAdditional> additionals) {
        final BigDecimal price = insuranceTarifDoubleMap.get(tarif);

        if ( additionals == null ) {
            return price;
        }

        BigDecimal percentage = BigDecimal.ONE;
        for (final InsuranceAdditional additional : additionals) {
            final BigDecimal additionalPercentage = insuranceAdditionalDoubleMap.get(additional);
            percentage = percentage.add(additionalPercentage);
        }

        return price.multiply(percentage);
    }

}
