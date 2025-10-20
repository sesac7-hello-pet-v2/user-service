package hello.pet.userservice.application.port.in;

import hello.pet.userservice.application.port.in.command.UpdateUserDetailCommand;
import hello.pet.userservice.application.port.out.result.UserDetailResult;

public interface UpdateUserUseCase {
    UserDetailResult updateUserDetail(UpdateUserDetailCommand cmd);
}
