package hello.pet.userservice.adapter.in.web;

import hello.pet.userservice.adapter.in.web.dto.*;
import hello.pet.userservice.application.port.in.command.UniqueCheckCommand;
import hello.pet.userservice.application.port.in.command.RegisterUserCommand;
import hello.pet.userservice.application.port.in.CreateUserUseCase;
import hello.pet.userservice.application.port.out.result.UniqueCheckResult;
import hello.pet.userservice.application.port.out.result.RegisterUserResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/exist")
    public ResponseEntity<UniqueCheckResponse> checkUnique(@RequestParam("field") String field,
                                                           @RequestParam("value") String value) {
        UniqueCheckCommand cmd = UniqueCheckRequest.toCommand(field, value);
        UniqueCheckResult result =  createUserUseCase.isUnique(cmd);
        UniqueCheckResponse res = UniqueCheckResponse.from(result);

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
