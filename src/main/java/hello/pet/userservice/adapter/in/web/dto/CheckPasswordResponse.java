package hello.pet.userservice.adapter.in.web.dto;

public record CheckPasswordResponse(boolean success, String message) {
    public static CheckPasswordResponse from(boolean isMatch) {
        if (isMatch) {
            return new CheckPasswordResponse(true, "비밀번호가 일치합니다.");
        } else {
            return new CheckPasswordResponse(false, "비밀번호가 일치하지 않습니다.");
        }
    }
}
