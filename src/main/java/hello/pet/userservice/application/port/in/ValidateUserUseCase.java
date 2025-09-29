package hello.pet.userservice.application.port.in;

import hello.pet.userservice.application.port.in.command.LoginValidationCommand;
import hello.pet.userservice.application.port.out.result.LoginValidationResult;

public interface ValidateUserUseCase {
    LoginValidationResult validate(LoginValidationCommand cmd);
}
