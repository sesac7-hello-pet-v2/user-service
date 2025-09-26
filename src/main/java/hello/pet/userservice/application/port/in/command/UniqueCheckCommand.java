package hello.pet.userservice.application.port.in.command;

public record UniqueCheckCommand(hello.pet.userservice.adapter.in.web.dto.UniqueField field, String value) {
}
