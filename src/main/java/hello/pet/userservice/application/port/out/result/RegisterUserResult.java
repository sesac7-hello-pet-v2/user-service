package hello.pet.userservice.application.port.out.result;

import hello.pet.userservice.domain.entity.User;
import hello.pet.userservice.domain.entity.UserDetail;

public record RegisterUserResult(Long id, String email, String nickname) {

    public static RegisterUserResult from(User user) {
        UserDetail detail = user.getUserDetail();
        return new RegisterUserResult(
                user.getId(),
                user.getEmail().value(),
                detail.getNickname().value()
        );
    }
}
