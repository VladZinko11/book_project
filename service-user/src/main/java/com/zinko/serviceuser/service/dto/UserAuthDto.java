package com.zinko.serviceuser.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthDto implements Serializable {
    Long id;
    @Size(max = 30, message = "{to.long.name.message}")
    @NotBlank
    String name;
    @Email(regexp = "^\\w+@[A-Za-z]{3,}\\.[a-z]{2,}", message = "{enter.valid.email.message}")
    @NotBlank
    String email;
    @Pattern(regexp = "^(?=.+\\d)(?=.+[a-z])(?=.+[A-Z]).*$", message = "{enter.valid.password.message}")
    @Length(min = 8, message = "{to.short.password.message}")
    @NotBlank
    String password;
}