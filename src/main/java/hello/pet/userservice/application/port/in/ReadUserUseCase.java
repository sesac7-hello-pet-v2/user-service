package hello.pet.userservice.application.port.in;

import hello.pet.userservice.application.port.in.command.ReadUserCommand;
import hello.pet.userservice.application.port.out.result.UserDetailResult;

public interface ReadUserUseCase {
    UserDetailResult getUserDetail(ReadUserCommand cmd);
}
