package hello.pet.userservice.adapter.out.persistence;

import hello.pet.userservice.application.dto.RegisterUserResponse;
import hello.pet.userservice.application.port.out.UserRepository;
import hello.pet.userservice.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    final private JpaUserRepository jpaUserRepository;

    @Override
    public RegisterUserResponse save(User newUser) {
        return RegisterUserResponse.from(jpaUserRepository.save(newUser));
    }
}
