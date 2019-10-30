package com.mg.insuranceapp.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(METHOD)
@Retention(RUNTIME)
@Constraint(validatedBy = ConsistentDateParametersValidator.class)
public @interface ConsistentDateParameters {
    String message() default "Enddate not present or is before startDate.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int typeIndex();
    int startDayIndex();
    int endDayIndex();

}
