package hello.pet.userservice.application.port.out;

import hello.pet.userservice.application.dto.RegisterUserResponse;
import hello.pet.userservice.domain.entity.User;

public interface UserRepository {
    RegisterUserResponse save(User user);
}
