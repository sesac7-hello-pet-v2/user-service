package hello.pet.userservice.adapter.in.web;

import hello.pet.userservice.adapter.in.web.dto.RegisterUserRequest;
import hello.pet.userservice.adapter.in.web.dto.RegisterUserResponse;
import hello.pet.userservice.application.port.in.CreateUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final CreateUserUseCase  createUserUseCase;

    @PostMapping
    public ResponseEntity<RegisterUserResponse> registerUser(@Valid @RequestBody RegisterUserRequest req) {
        return ResponseEntity.status(200).body(createUserUseCase.register(req));
    }
}
