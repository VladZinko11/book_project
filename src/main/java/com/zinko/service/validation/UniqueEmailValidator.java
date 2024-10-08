package com.zinko.service.validation;

import com.zinko.data.model.User;
import com.zinko.data.repository.UserRepository;
import com.zinko.service.dto.UserAuthDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, UserAuthDto> {
    private final UserRepository userRepository;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserAuthDto value, ConstraintValidatorContext context) {
        Optional<User> optionalUser = userRepository.findByEmail(value.getEmail());
        if (optionalUser.isEmpty()) {
            return true;
        }
        User user = optionalUser.get();
        return user.getId().equals(value.getId());
    }

}
