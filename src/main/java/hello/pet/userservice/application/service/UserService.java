package hello.pet.userservice.application.service;

import hello.pet.userservice.adapter.in.web.dto.RegisterUserRequest;
import hello.pet.userservice.adapter.in.web.dto.RegisterUserResponse;
import hello.pet.userservice.application.port.in.CreateUserUseCase;
import hello.pet.userservice.application.port.out.UserRepository;
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
    public RegisterUserResponse register(RegisterUserRequest req) {
        String encodedPassword = passwordEncoder.encode(req.getPassword());
        return userRepository.save(req.toEntity(encodedPassword));
    }
}
