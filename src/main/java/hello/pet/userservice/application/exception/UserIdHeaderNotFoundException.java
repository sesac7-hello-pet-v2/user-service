package hello.pet.userservice.application.exception;

public class UserIdHeaderNotFoundException extends RuntimeException {
    public UserIdHeaderNotFoundException(String message) {
        super(message);
    }
}
