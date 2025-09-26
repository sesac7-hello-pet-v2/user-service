package hello.pet.userservice.adapter.in.web.dto;

import hello.pet.userservice.application.port.in.command.UniqueCheckCommand;

public record UniqueCheckRequest(
        UniqueField field,
        String value
) {
    public static UniqueCheckCommand toCommand(UniqueCheckRequest request) {
        return new UniqueCheckCommand(request.field(), request.value());
    }
}
