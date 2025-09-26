package hello.pet.userservice.application.dto;

import hello.pet.userservice.domain.entity.User;

public record RegisterUserResponse(Long id, String email) {

    public static RegisterUserResponse from(User save) {
        return new RegisterUserResponse(save.getId(), save.getEmail().getValue());
    }
}
