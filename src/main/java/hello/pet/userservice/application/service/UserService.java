package hello.pet.userservice.application.service;

import hello.pet.userservice.adapter.in.web.dto.RegisterUserResponse;
import hello.pet.userservice.application.port.in.command.RegisterUserCommand;
import hello.pet.userservice.application.port.in.CreateUserUseCase;
import hello.pet.userservice.application.port.out.UserRepository;
import hello.pet.userservice.application.port.out.result.RegisterUserResult;
import hello.pet.userservice.domain.entity.User;
import hello.pet.userservice.domain.entity.UserDetail;
import hello.pet.userservice.domain.entity.UserRole;
import hello.pet.userservice.domain.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterUserResult register(RegisterUserCommand cmd) {

        User user = createUser(cmd);
        User savedUser = userRepository.save(user);
        log.info("유저가 등록 되었습니다! 새로운 유저의 id: {}", savedUser.getId());
        return RegisterUserResult.from(savedUser);
    }

    private User createUser(RegisterUserCommand cmd) {

        String encodedPassword  = passwordEncoder.encode(cmd.password());

        User user = new User(
                null,
                new Email(cmd.email()),
                new Password(encodedPassword),
                UserRole.valueOf(cmd.role()),
                new UserActivation(true),
                null
        );

        UserDetail ud = new UserDetail(
                null,
                new Nickname(cmd.nickname()),
                cmd.userProfileUrl(),
                cmd.address(),
                new PhoneNumber(cmd.phoneNumber()),
                null
        );

        user.linkUserDetail(ud);

        return user;
    }
}
