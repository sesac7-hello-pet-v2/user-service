package hello.pet.userservice.adapter.in.web.dto;

import hello.pet.userservice.application.port.in.command.UniqueCheckCommand;

public record UniqueCheckRequest(
        UniqueField field,
        String value
) {

    public static UniqueCheckCommand toCommand(String field, String value) {
        return new UniqueCheckCommand(UniqueField.valueOf(field), value);
    }
}
