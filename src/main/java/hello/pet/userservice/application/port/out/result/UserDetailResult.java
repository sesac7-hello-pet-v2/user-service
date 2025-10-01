package hello.pet.userservice.application.port.out.result;

import hello.pet.userservice.domain.entity.User;
import hello.pet.userservice.domain.entity.UserDetail;

public record UserDetailResult(
        String email,
        String nickname,
        String username,
        String address,
        String profileUrl,
        String phoneNumber) {
    public static UserDetailResult from(User user, UserDetail ud) {
        return new UserDetailResult(
                user.getEmail().value(),
                ud.getNickname().value(),
                ud.getUsername(),
                ud.getAddress(),
                ud.getUserProfileUrl(),
                ud.getPhoneNumber().value()
        );
    }
}
