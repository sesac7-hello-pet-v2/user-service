package hello.pet.userservice.application.port.in;

public interface DeleteUserUseCase {
    boolean checkPassword(Long userId, String password);

    void deleteUser(Long userId);
}
