package hello.pet.userservice.adapter.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginValidationRequest(
        @NotBlank @Email String email,
        @NotBlank String password) {
}
