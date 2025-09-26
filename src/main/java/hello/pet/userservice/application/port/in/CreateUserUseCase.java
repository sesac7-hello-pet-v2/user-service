package hello.pet.userservice.application.port.in;

import hello.pet.userservice.application.dto.RegisterUserRequest;
import hello.pet.userservice.application.dto.RegisterUserResponse;
import jakarta.validation.Valid;

public interface CreateUserUseCase {
    RegisterUserResponse register(@Valid RegisterUserRequest req);
}
