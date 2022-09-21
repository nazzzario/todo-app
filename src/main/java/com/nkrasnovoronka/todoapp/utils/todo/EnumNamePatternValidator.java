package com.nkrasnovoronka.todoapp.utils.todo;

import com.nkrasnovoronka.todoapp.anotations.EnumNamePattern;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumNamePatternValidator implements ConstraintValidator<EnumNamePattern, Enum<?>> {

  private Pattern pattern;

  @Override
  public void initialize(EnumNamePattern constraintAnnotation) {
    try {
      pattern = Pattern.compile(constraintAnnotation.regexp());
    } catch (PatternSyntaxException e) {
      throw new IllegalArgumentException(e.getMessage());
    }

  }

  @Override
  public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
    if (Objects.isNull(value)) {
      return false;
    }
    var matcher = pattern.matcher(value.name());
    return matcher.matches();
  }
}
