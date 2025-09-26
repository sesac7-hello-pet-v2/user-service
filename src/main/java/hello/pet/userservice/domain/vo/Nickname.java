package hello.pet.userservice.domain.vo;

import java.util.regex.Pattern;

public record Nickname(String value) {

    private static final Pattern KOREAN_REGEX =
            Pattern.compile("^[가-힣]+$");
    private static final Pattern ENGLISH_REGEX =
            Pattern.compile("^[A-Za-z]+$");

    public Nickname {
        if (value == null || (!KOREAN_REGEX.matcher(value).matches() &&
                !ENGLISH_REGEX.matcher(value).matches())) {
            throw new IllegalArgumentException("유효하지 않은 닉네임 형식입니다!");
        }
    }
}
