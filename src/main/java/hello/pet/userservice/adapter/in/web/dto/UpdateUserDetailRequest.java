package hello.pet.userservice.adapter.in.web.dto;

import hello.pet.userservice.application.port.in.command.UpdateUserDetailCommand;

public record UpdateUserDetailRequest(
        String nickname,
        String address,
        String userProfileUrl) {
    public UpdateUserDetailCommand toCommand(Long userId) {
        return new UpdateUserDetailCommand(nickname, address, userProfileUrl, userId);
    }
}
