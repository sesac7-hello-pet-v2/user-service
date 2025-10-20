package hello.pet.userservice.adapter.out.persistence;

import hello.pet.userservice.adapter.in.web.dto.UniqueField;
import hello.pet.userservice.application.port.out.UserRepository;
import hello.pet.userservice.domain.entity.User;
import hello.pet.userservice.domain.vo.Email;
import hello.pet.userservice.domain.vo.Nickname;
import hello.pet.userservice.domain.vo.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final JpaUserDetailRepository jpaUserDetailRepository;

    @Override
    public User save(User user) {
        return jpaUserRepository.save(user);
    }

    @Override
    public boolean findByField(UniqueField field, String value) {
        return switch (field) {
            case EMAIL -> jpaUserRepository.existsByEmail(new Email(value));
            case NICKNAME -> jpaUserDetailRepository.existsByNickname(new Nickname(value));
            case PHONE -> jpaUserDetailRepository.existsByPhoneNumber(new PhoneNumber(value));
        };
    }

    @Override
    public Optional<User> findByEmail(String value) {
        return jpaUserRepository.findByEmail(new Email(value));
    }

    @Override
    public Optional<User> findById(Long userId) {
        return jpaUserRepository.findById(userId);
    }
}
