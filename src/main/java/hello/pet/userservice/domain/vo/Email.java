package hello.pet.userservice.domain.vo;

import java.util.regex.Pattern;

public record Email(String value) {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,}$");

    public Email {
        if (value == null || !EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다!");
        }
    }

    public String getValue() {
        return value;
    }
}
