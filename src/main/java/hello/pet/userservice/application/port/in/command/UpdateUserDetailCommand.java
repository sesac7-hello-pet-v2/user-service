package hello.pet.userservice.application.port.in.command;

public record UpdateUserDetailCommand(String nickname,
                                      String address,
                                      String userProfileUrl,
                                      Long userId) {
}
