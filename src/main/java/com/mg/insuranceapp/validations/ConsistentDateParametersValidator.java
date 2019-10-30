package com.mg.insuranceapp.validations;

import com.mg.insuranceapp.enums.InsuranceType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import java.time.LocalDate;

import static com.mg.insuranceapp.enums.InsuranceType.YEARLY;
import static javax.validation.constraintvalidation.ValidationTarget.PARAMETERS;

@SupportedValidationTarget(PARAMETERS)
public class ConsistentDateParametersValidator implements ConstraintValidator<ConsistentDateParameters, Object[]> {

    private int typeIndex, startDateIndex, endDateIndex;

    @Override
    public void initialize(final ConsistentDateParameters constraintAnnotation) {
        typeIndex = constraintAnnotation.typeIndex();
        startDateIndex = constraintAnnotation.startDayIndex();
        endDateIndex = constraintAnnotation.endDayIndex();
    }

    @Override
    public boolean isValid(final Object[] value, final ConstraintValidatorContext context) {
        final InsuranceType type = (InsuranceType) value[typeIndex];
        final LocalDate startDate = (LocalDate) value[startDateIndex];
        final LocalDate endDate = (LocalDate) value[endDateIndex];

        return YEARLY.equals(type) || !(endDate == null || endDate.isBefore(startDate));
    }
}
