package hello.pet.userservice.application.port.in.command;

public record RegisterUserCommand(
        String email,
        String password,
        String  role,
        String nickname,
        String username,
        String address,
        String phoneNumber,
        String userProfileUrl
) {
}
