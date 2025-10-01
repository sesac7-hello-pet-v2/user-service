package hello.pet.userservice.adapter.in.web;

import hello.pet.userservice.adapter.in.web.dto.*;
import hello.pet.userservice.application.port.in.CreateUserUseCase;
import hello.pet.userservice.application.port.in.DeleteUserUseCase;
import hello.pet.userservice.application.port.in.ReadUserUseCase;
import hello.pet.userservice.application.port.in.UpdateUserUseCase;
import hello.pet.userservice.application.port.in.command.ReadUserCommand;
import hello.pet.userservice.application.port.in.command.RegisterUserCommand;
import hello.pet.userservice.application.port.in.command.UniqueCheckCommand;
import hello.pet.userservice.application.port.in.command.UpdateUserDetailCommand;
import hello.pet.userservice.application.port.out.result.RegisterUserResult;
import hello.pet.userservice.application.port.out.result.UniqueCheckResult;
import hello.pet.userservice.application.port.out.result.UserDetailResult;
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
    private final ReadUserUseCase readUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    @PostMapping
    public ResponseEntity<RegisterUserResponse> registerUser(@Valid @RequestBody RegisterUserRequest req) {

        RegisterUserCommand cmd = req.toCommand();
        RegisterUserResult result =  createUserUseCase.register(cmd);
        RegisterUserResponse res = RegisterUserResponse.from(result);

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping
    public ResponseEntity<UserDetailResponse> getUserDetail(@RequestHeader(value = "X-User-Id", required = false) Long userId) {
        ReadUserCommand cmd = new ReadUserCommand(userId);
        UserDetailResult result = readUserUseCase.getUserDetail(cmd);
        UserDetailResponse res = UserDetailResponse.from(result);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PutMapping
    public ResponseEntity<UserDetailResponse> updateUserDetail(@Valid @RequestBody UpdateUserDetailRequest req,
                                                                 @RequestHeader(value = "X-User-Id", required = false) Long userId) {
        UpdateUserDetailCommand cmd = req.toCommand(userId);
        UserDetailResult result = updateUserUseCase.updateUserDetail(cmd);
        UserDetailResponse res = UserDetailResponse.from(result);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/exist")
    public ResponseEntity<UniqueCheckResponse> checkUnique(@RequestParam("field") String field,
                                                           @RequestParam("value") String value) {
        UniqueCheckCommand cmd = UniqueCheckRequest.toCommand(field, value);
        UniqueCheckResult result =  createUserUseCase.isUnique(cmd);
        UniqueCheckResponse res = UniqueCheckResponse.from(result);

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

   @PostMapping("check-password" )
    public ResponseEntity<CheckPasswordResponse> checkPassword(@RequestBody CheckPasswordRequest req,
                                                               @RequestHeader(value = "X-User-Id", required = false) Long userId) {
        boolean isMatch = deleteUserUseCase.checkPassword(userId, req.password());
        CheckPasswordResponse res = CheckPasswordResponse.from(isMatch);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestHeader(value = "X-User-Id", required = false) Long userId) {
        deleteUserUseCase.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
