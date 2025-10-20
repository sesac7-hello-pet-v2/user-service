package hello.pet.userservice.adapter.in.web;

import hello.pet.userservice.adapter.in.web.dto.LoginValidationRequest;
import hello.pet.userservice.adapter.in.web.dto.UserDetailResponse;
import hello.pet.userservice.adapter.out.web.dto.LoginValidationResponse;
import hello.pet.userservice.application.port.in.ReadUserUseCase;
import hello.pet.userservice.application.port.in.ValidateUserUseCase;
import hello.pet.userservice.application.port.in.command.LoginValidationCommand;
import hello.pet.userservice.application.port.in.command.ReadUserCommand;
import hello.pet.userservice.application.port.out.result.LoginValidationResult;
import hello.pet.userservice.application.port.out.result.UserDetailResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/v1/users")
@RequiredArgsConstructor
public class InternalUserController {

    private final ValidateUserUseCase validateUserUseCase;
    private final ReadUserUseCase readUserUseCase;

    @PostMapping(
            value = "/validate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<LoginValidationResponse> validateLoginUser(@Valid @RequestBody LoginValidationRequest req) {
        LoginValidationCommand cmd = LoginValidationCommand.from(req);
        LoginValidationResult res  = validateUserUseCase.validate(cmd);
        LoginValidationResponse response = LoginValidationResponse.from(res);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(
            value = "/{userId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserDetailResponse> getUserDetail(@PathVariable Long userId) {
        ReadUserCommand cmd = new ReadUserCommand(userId);
        UserDetailResult result = readUserUseCase.getUserDetail(cmd);
        UserDetailResponse res = UserDetailResponse.from(result);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
