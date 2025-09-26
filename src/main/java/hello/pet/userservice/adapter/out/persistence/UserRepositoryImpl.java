package hello.pet.userservice.adapter.out.persistence;

import hello.pet.userservice.application.port.out.UserRepository;
import hello.pet.userservice.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    final private JpaUserRepository jpaUserRepository;

    @Override
    public User save(User user) {
        return jpaUserRepository.save(user);
    }
}
