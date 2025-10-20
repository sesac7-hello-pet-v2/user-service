package hello.pet.userservice.application.exception;

public class PasswordNotMatchedException extends RuntimeException{
    public PasswordNotMatchedException(String message) {
        super(message);
    }
}
