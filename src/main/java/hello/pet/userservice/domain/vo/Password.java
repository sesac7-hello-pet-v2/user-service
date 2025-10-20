package hello.pet.userservice.domain.vo;

import java.util.regex.Pattern;

public record Password(String value) {
    private static final int MIN_LENGTH = 6;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).+$");

    public Password {
        if (value == null || value.length() < MIN_LENGTH) {
            throw new IllegalArgumentException("비밀번호는 최소 " + MIN_LENGTH + "자 이상이어야 합니다.");
        }
        if (!PASSWORD_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("비밀번호는 영문자, 숫자, 특수문자를 각각 최소 한 개씩 포함해야 합니다.");
        }
    }
}
