package com.zinko.service.impl;

import com.zinko.service.ErrorService;
import com.zinko.service.exception.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ErrorServiceImpl implements ErrorService {
    @Override
    public void checkErrors(Errors errors) {
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
    }

    @Override
    public Map<String, List<String>> mapErrors(Errors errors) {
        return errors.getAllErrors().stream()
                .collect(
                        Collectors.groupingBy(
                                ObjectError::getObjectName,
                                Collectors.mapping(ObjectError::getDefaultMessage, Collectors.toList())
                        ));
    }
}
