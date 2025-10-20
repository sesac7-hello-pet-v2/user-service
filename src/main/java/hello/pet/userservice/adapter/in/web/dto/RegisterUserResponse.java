package hello.pet.userservice.adapter.in.web.dto;

import hello.pet.userservice.application.port.out.result.RegisterUserResult;

public record RegisterUserResponse(Long id, String email, String nickname) {


    public static RegisterUserResponse from(RegisterUserResult result) {
        return new RegisterUserResponse(result.id(), result.email(), result.nickname());
    }
}
