package hello.pet.userservice.domain.vo;

public record UserActivation(boolean value) {
    public UserActivation inActivate() {
        if (!value) {
            throw new IllegalStateException("이미 비활성화된 사용자입니다.");
        }
        return new UserActivation(false);
    }
}
