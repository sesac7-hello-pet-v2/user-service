package hello.pet.userservice.application.port.out.result;

public record UniqueCheckResult(hello.pet.userservice.adapter.in.web.dto.UniqueField field, String value, boolean isUnique,
                                String message) {
}
