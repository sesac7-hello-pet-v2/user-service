package hello.pet.userservice.adapter.in.web.dto;

import hello.pet.userservice.application.port.out.result.UserDetailResult;

public record UserDetailResponse(
        String email,
        String nickname,
        String username,
        String address,
        String profileUrl,
        String phoneNumber
) {
    public static UserDetailResponse from(UserDetailResult result) {
        return new UserDetailResponse(
                result.email(),
                result.nickname(),
                result.username(),
                result.address(),
                result.profileUrl(),
                result.phoneNumber()
        );
    }
}
