package hello.pet.userservice.application.port.out;

import hello.pet.userservice.adapter.in.web.dto.RegisterUserResponse;
import hello.pet.userservice.domain.entity.User;

public interface UserRepository {
    RegisterUserResponse save(User user);
}
