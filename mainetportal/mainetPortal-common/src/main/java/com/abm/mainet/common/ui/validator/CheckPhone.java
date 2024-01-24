package com.abm.mainet.common.ui.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckPhoneValidator.class)
@Documented
public @interface CheckPhone {

    String message() default "enter name in uppercase only";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
