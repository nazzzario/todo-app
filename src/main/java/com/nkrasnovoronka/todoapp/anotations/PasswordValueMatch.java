package com.nkrasnovoronka.todoapp.anotations;

import com.nkrasnovoronka.todoapp.utils.password.PasswordFieldsValueMatchValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy = PasswordFieldsValueMatchValidator.class)
public @interface PasswordValueMatch {
  String message() default "Fields values don't match!";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };

  String field();

  String fieldMatch();

  @Target({ ElementType.TYPE })
  @Retention(RetentionPolicy.RUNTIME)
  @interface List {
    PasswordValueMatch[] value();
  }
}
