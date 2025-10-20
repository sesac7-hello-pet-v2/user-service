package hello.pet.userservice.adapter.in.web.dto;

import hello.pet.userservice.application.port.in.command.RegisterUserCommand;
import hello.pet.userservice.domain.entity.UserRole;
import jakarta.validation.constraints.*;
import lombok.Getter;

public record RegisterUserRequest(
        @NotBlank(message = "사용자 이름은 필수입니다")
        @Email(message = "유효한 이메일 형식이어야 합니다")
        String email,

        @Getter
        @NotBlank(message = "비밀번호는 필수입니다")
        @Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).+$",
                message = "비밀번호는 영문자, 숫자, 특수문자를 각각 최소 한 개씩 포함해야 합니다."
        )
        String password,

        @NotNull(message = "사용자 권한은 필수입니다")
        String role,

        @NotBlank(message = "닉네임은 필수입니다")
        @Pattern(
                regexp = "^[가-힣a-zA-Z0-9]{2,10}$",
                message = "닉네임은 한글, 영문, 숫자 조합으로 2-10자여야 합니다."
        )
        String nickname,

        @NotBlank(message = "사용자 이름은 필수입니다")
        @Size(min = 2, max = 10, message = "사용자 이름은 2-10자 사이여야 합니다")
        String username,

        @NotBlank(message = "주소는 필수입니다")
        String address,

        @NotBlank(message = "사용자 전화번호는 필수입니다")
        @Pattern(
                regexp = "^\\d{10,11}$",
                message = "휴대폰 번호는 숫자 10자리 또는 11자리여야 합니다."
        )
        String phoneNumber,
        String userProfileUrl
) {
    public RegisterUserCommand toCommand() {
        return new RegisterUserCommand(
                email,
                password,
                role,
                nickname,
                username,
                address,
                phoneNumber,
                userProfileUrl
        );
    }
}
