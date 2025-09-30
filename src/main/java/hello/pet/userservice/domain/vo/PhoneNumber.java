package hello.pet.userservice.domain.vo;

import java.util.regex.Pattern;

public record PhoneNumber(String value) {
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\d{10,11}$");

    public PhoneNumber {
        if (value == null || !PHONE_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("휴대폰 번호는 숫자 10자리 또는 11자리여야 합니다.");
        }
    }
}
