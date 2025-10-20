package hello.pet.userservice.domain.vo;

import java.util.regex.Pattern;

public record Nickname(String value) {

    private static final Pattern NICKNAME_PATTERN =
            Pattern.compile("^[가-힣a-zA-Z0-9]{2,10}$");

    public Nickname {
        if (value == null || !NICKNAME_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("닉네임은 한글, 영문, 숫자 조합으로 2-10자여야 합니다!");
        }
    }
}
