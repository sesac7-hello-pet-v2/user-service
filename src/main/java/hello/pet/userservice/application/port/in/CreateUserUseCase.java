package hello.pet.userservice.application.port.in;

import hello.pet.userservice.adapter.in.web.dto.RegisterUserResponse;
import hello.pet.userservice.application.port.in.command.RegisterUserCommand;
import hello.pet.userservice.application.port.out.result.RegisterUserResult;

public interface CreateUserUseCase {
    RegisterUserResult register(RegisterUserCommand cmd);
}
