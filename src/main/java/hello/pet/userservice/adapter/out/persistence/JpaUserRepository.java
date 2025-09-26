package hello.pet.userservice.adapter.out.persistence;

import hello.pet.userservice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User,Long> {
}
