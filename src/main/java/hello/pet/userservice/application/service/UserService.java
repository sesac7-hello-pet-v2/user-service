package hello.pet.userservice.application.service;

import hello.pet.userservice.application.exception.PasswordNotMatchedException;
import hello.pet.userservice.application.exception.UserNotFoundException;
import hello.pet.userservice.application.port.in.ValidateUserUseCase;
import hello.pet.userservice.application.port.in.command.LoginValidationCommand;
import hello.pet.userservice.application.port.in.command.UniqueCheckCommand;
import hello.pet.userservice.application.port.in.command.RegisterUserCommand;
import hello.pet.userservice.application.port.in.CreateUserUseCase;
import hello.pet.userservice.application.port.out.UserRepository;
import hello.pet.userservice.application.port.out.result.LoginValidationResult;
import hello.pet.userservice.application.port.out.result.UniqueCheckResult;
import hello.pet.userservice.application.port.out.result.RegisterUserResult;
import hello.pet.userservice.domain.entity.User;
import hello.pet.userservice.domain.entity.UserDetail;
import hello.pet.userservice.domain.entity.UserRole;
import hello.pet.userservice.domain.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements CreateUserUseCase, ValidateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterUserResult register(RegisterUserCommand cmd) {

        User user = createUser(cmd);
        User savedUser = userRepository.save(user);
        log.info("유저가 등록 되었습니다! 새로운 유저의 id: {}", savedUser.getId());
        return RegisterUserResult.from(savedUser);
    }

    @Override
    public UniqueCheckResult isUnique(UniqueCheckCommand cmd) {
        boolean exist = userRepository.findByField(cmd.field(), cmd.value());
        String message = exist ? "이미 사용중인 " + cmd.field() + "입니다." : "사용 가능한 " + cmd.field() + "입니다.";
        return new UniqueCheckResult(cmd.field(), cmd.value(), !exist, message);
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

    @Override
    public LoginValidationResult validate(LoginValidationCommand cmd) {
        try {

            User user = userRepository.findByEmail(cmd.email())
                    .orElseThrow(() -> new UserNotFoundException("등록된 유저가 없습니다."));

            if (!user.isPasswordMatched(cmd.password(), passwordEncoder)) {
                throw new PasswordNotMatchedException("비밀번호가 일치하지 않습니다.");
            }

            return LoginValidationResult.success(user);
        } catch (UserNotFoundException e){
            return LoginValidationResult.fail("등록된 유저가 없습니다.");
        } catch (PasswordNotMatchedException e) {
            return LoginValidationResult.fail("비밀번호가 일치하지 않습니다.");
        }
    }
}
