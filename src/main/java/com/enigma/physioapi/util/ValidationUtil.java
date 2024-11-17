package com.enigma.physioapi.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ValidationUtil {
  private final Validator validator;

  public void validate(Object object) {
    Set<ConstraintViolation<Object>> errors = validator.validate(object);
    if (!errors.isEmpty())
      throw new ConstraintViolationException(errors);
  }
}
