package hello.pet.userservice.adapter.in.web.dto;

import hello.pet.userservice.application.port.out.result.UniqueCheckResult;

public record UniqueCheckResponse(String field, String value, boolean isUnique, String message) {
    public static UniqueCheckResponse from(UniqueCheckResult res) {
        return new UniqueCheckResponse(
                res.field().name(),
                res.value(),
                res.isUnique(),
                res.message()
        );
    }
}
