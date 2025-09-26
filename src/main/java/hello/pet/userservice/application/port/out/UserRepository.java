package hello.pet.userservice.application.port.out;

import hello.pet.userservice.domain.entity.User;

public interface UserRepository {
    User save(User user);
}
