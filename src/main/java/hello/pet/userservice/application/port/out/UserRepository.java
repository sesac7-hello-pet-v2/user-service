package hello.pet.userservice.application.port.out;

import hello.pet.userservice.adapter.in.web.dto.UniqueField;
import hello.pet.userservice.domain.entity.User;

public interface UserRepository {
    User save(User user);

    boolean findByField(UniqueField field, String value);
}
