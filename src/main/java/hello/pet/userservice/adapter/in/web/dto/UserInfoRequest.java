package hello.pet.userservice.adapter.in.web.dto;

public record UserInfoRequest(
        String email,
        String nickname,
        String phone,
        String address,
        String profileUrl
) {
}
