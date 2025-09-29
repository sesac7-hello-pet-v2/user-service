package hello.pet.userservice.application.port.in.command;

import hello.pet.userservice.adapter.in.web.dto.LoginValidationRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginValidationCommand(String email,
                                     String password) {
    public static LoginValidationCommand from(@Valid LoginValidationRequest req) {
        return new LoginValidationCommand(req.email(), req.password());
    }
}
