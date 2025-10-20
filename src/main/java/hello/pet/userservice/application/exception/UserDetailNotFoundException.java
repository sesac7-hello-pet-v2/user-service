package hello.pet.userservice.application.exception;

public class UserDetailNotFoundException extends RuntimeException {
    public UserDetailNotFoundException(String message) {
        super(message);
    }
}
