package hello.pet.userservice.application.service;

import hello.pet.userservice.adapter.in.web.dto.UniqueField;
import hello.pet.userservice.application.exception.PasswordNotMatchedException;
import hello.pet.userservice.application.exception.UserDetailNotFoundException;
import hello.pet.userservice.application.exception.UserIdHeaderNotFoundException;
import hello.pet.userservice.application.exception.UserNotFoundException;
import hello.pet.userservice.application.port.in.*;
import hello.pet.userservice.application.port.in.command.*;
import hello.pet.userservice.application.port.out.UserRepository;
import hello.pet.userservice.application.port.out.result.LoginValidationResult;
import hello.pet.userservice.application.port.out.result.RegisterUserResult;
import hello.pet.userservice.application.port.out.result.UniqueCheckResult;
import hello.pet.userservice.application.port.out.result.UserDetailResult;
import hello.pet.userservice.domain.entity.User;
import hello.pet.userservice.domain.entity.UserDetail;
import hello.pet.userservice.domain.entity.UserRole;
import hello.pet.userservice.domain.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements CreateUserUseCase, ReadUserUseCase, UpdateUserUseCase, DeleteUserUseCase, ValidateUserUseCase{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterUserResult register(RegisterUserCommand cmd) {
        validateUniqueness(cmd);

        User user = createUser(cmd);
        User savedUser = userRepository.save(user);
        log.info("유저가 등록 되었습니다! 새로운 유저의 id: {}", savedUser.getId());
        return RegisterUserResult.from(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetailResult getUserDetail(ReadUserCommand cmd) {
        log.info("유저 상세 정보 조회! user id: {}", cmd.userId());

        if (cmd.userId() == null) {
            throw new UserIdHeaderNotFoundException("헤더에 X-User-Id가 없습니다.");
        }

        User user = userRepository.findById(cmd.userId())
                .orElseThrow(() -> new UserNotFoundException("등록된 유저가 없습니다."));

        log.info("유저 상세 정보 조회 완료! user id: {}", cmd.userId());

        UserDetail ud = user.getUserDetail();

        if (ud == null) {
            throw new UserDetailNotFoundException("유저 상세 정보가 없습니다.");
        }

        return UserDetailResult.from(user, ud);
    }

    @Override
    public UserDetailResult updateUserDetail(UpdateUserDetailCommand cmd) {
        log.info("유저 상세 정보 수정! user id: {}", cmd.userId());

        if (cmd.userId() == null) {
            throw new UserIdHeaderNotFoundException("헤더에 X-User-Id가 없습니다.");
        }

        User user = userRepository.findById(cmd.userId())
                .orElseThrow(() -> new UserNotFoundException("등록된 유저가 없습니다."));

        UserDetail ud = user.getUserDetail();

        if (ud == null) {
            throw new UserDetailNotFoundException("유저 상세 정보가 없습니다.");
        }

        ud.update(
                cmd.nickname(),
                cmd.address(),
                cmd.userProfileUrl()
        );

        log.info("유저 상세 정보 수정 완료! user id: {}", cmd.userId());
        return UserDetailResult.from(user, ud);
    }

    @Override
    public void deleteUser(Long userId) {
        log.info("유저 삭제! user id: {}", userId);

        if (userId == null) {
            throw new UserIdHeaderNotFoundException("헤더에 X-User-Id가 없습니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("등록된 유저가 없습니다."));

        user.inActivate();

        log.info("유저 삭제 완료! user id: {}", userId);
    }

    @Override
    @Transactional(readOnly = true)
    public UniqueCheckResult isUnique(UniqueCheckCommand cmd) {
        boolean exist = userRepository.findByField(cmd.field(), cmd.value());
        String message = exist ? "이미 사용중인 " + cmd.field() + "입니다." : "사용 가능한 " + cmd.field() + "입니다.";
        return new UniqueCheckResult(cmd.field(), cmd.value(), !exist, message);
    }

    @Override
    @Transactional(readOnly = true)
    public LoginValidationResult validate(LoginValidationCommand cmd) {
        try {

            User user = userRepository.findByEmail(cmd.email())
                    .orElseThrow(() -> new UserNotFoundException("등록된 유저가 없습니다."));

            if (!user.isPasswordMatched(cmd.password(), passwordEncoder)) {
                throw new PasswordNotMatchedException("비밀번호가 일치하지 않습니다.");
            }

            log.info("유저 로그인 성공! user id: {}", user.getId());

            return LoginValidationResult.success(user);
        } catch (UserNotFoundException e){
            return LoginValidationResult.fail("등록된 유저가 없습니다.");
        } catch (PasswordNotMatchedException e) {
            return LoginValidationResult.fail("비밀번호가 일치하지 않습니다.");
        }
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
                cmd.username(),
                new Nickname(cmd.nickname()),
                cmd.userProfileUrl(),
                cmd.address(),
                new PhoneNumber(cmd.phoneNumber()),
                null
        );

        user.linkUserDetail(ud);

        return user;
    }

    private void validateUniqueness(RegisterUserCommand cmd) {
        checkUnique(UniqueField.EMAIL, cmd.email());
        checkUnique(UniqueField.NICKNAME, cmd.nickname());
        checkUnique(UniqueField.PHONE, cmd.phoneNumber());
    }

    private void checkUnique(UniqueField field, String value) {
        UniqueCheckResult result = isUnique(new UniqueCheckCommand(field, value));
        if (!result.isUnique()) {
            throw new IllegalArgumentException(result.message());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkPassword(Long userId, String password) {
        log.info("유저 패스워드 체크! user id: {}", userId);

        if (userId == null) {
            throw new UserIdHeaderNotFoundException("헤더에 X-User-Id가 없습니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("등록된 유저가 없습니다."));

        log.info("유저 패스워드 체크 완료! user id: {}", userId);
        return user.isPasswordMatched(password, passwordEncoder);
    }

}
