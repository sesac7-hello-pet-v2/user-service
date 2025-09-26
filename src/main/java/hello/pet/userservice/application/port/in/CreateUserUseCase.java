package hello.pet.userservice.application.port.in;

import hello.pet.userservice.application.port.in.command.UniqueCheckCommand;
import hello.pet.userservice.application.port.in.command.RegisterUserCommand;
import hello.pet.userservice.application.port.out.result.UniqueCheckResult;
import hello.pet.userservice.application.port.out.result.RegisterUserResult;

public interface CreateUserUseCase {
    RegisterUserResult register(RegisterUserCommand cmd);

    UniqueCheckResult isUnique(UniqueCheckCommand cmd);
}
