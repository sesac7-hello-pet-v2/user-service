package hello.pet.userservice.application.port.in;

import hello.pet.userservice.adapter.in.web.dto.RegisterUserRequest;
import hello.pet.userservice.adapter.in.web.dto.RegisterUserResponse;
import jakarta.validation.Valid;

public interface CreateUserUseCase {
    RegisterUserResponse register(@Valid RegisterUserRequest req);
}
