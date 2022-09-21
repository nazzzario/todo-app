package com.nkrasnovoronka.todoapp.anotations;

import com.nkrasnovoronka.todoapp.utils.todo.EnumNamePatternValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Documented
@Constraint(validatedBy = EnumNamePatternValidator.class)
public @interface EnumNamePattern {
  String regexp();

  String message() default "must match \"{regexp}\"";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
