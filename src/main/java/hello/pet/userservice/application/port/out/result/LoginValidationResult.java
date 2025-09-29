package hello.pet.userservice.application.port.out.result;

import hello.pet.userservice.domain.entity.User;

public record LoginValidationResult(boolean valid,
                                    Long id,
                                    String nickname,
                                    String role,
                                    String profileUrl,
                                    String reason) {
    public static LoginValidationResult success(User user) {
        return new LoginValidationResult(true,
                user.getId(),
                user.getUserDetail().getNickname().value(),
                user.getRole().name(),
                user.getUserDetail().getUserProfileUrl(),
                null
        );
    }

    public static LoginValidationResult fail(String reason) {
        return new LoginValidationResult(false, null, null, null, null, reason);
    }
}
