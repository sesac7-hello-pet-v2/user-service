package hello.pet.userservice.adapter.in.web;

import hello.pet.userservice.adapter.in.web.dto.RegisterUserRequest;
import hello.pet.userservice.adapter.in.web.dto.RegisterUserResponse;
import hello.pet.userservice.application.port.in.command.RegisterUserCommand;
import hello.pet.userservice.application.port.in.CreateUserUseCase;
import hello.pet.userservice.application.port.out.result.RegisterUserResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

        RegisterUserCommand cmd = req.toCommand();
        RegisterUserResult result =  createUserUseCase.register(cmd);
        RegisterUserResponse res = RegisterUserResponse.from(result);

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
}
