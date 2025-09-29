package hello.pet.userservice.adapter.out.web.dto;

import hello.pet.userservice.application.port.out.result.LoginValidationResult;

public record LoginValidationResponse(
        boolean valid,
        Long id,
        String nickname,
        String role,
        String profileUrl,
        String reason) {
    public static LoginValidationResponse from(LoginValidationResult res) {
        return new LoginValidationResponse(
                res.valid(),
                res.id(),
                res.nickname(),
                res.role(),
                res.profileUrl(),
                res.reason()
        );
    }
}
