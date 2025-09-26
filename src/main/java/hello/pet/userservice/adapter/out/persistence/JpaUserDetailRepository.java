package hello.pet.userservice.adapter.out.persistence;

import hello.pet.userservice.domain.entity.UserDetail;
import hello.pet.userservice.domain.vo.Nickname;
import hello.pet.userservice.domain.vo.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserDetailRepository extends JpaRepository<UserDetail, Long> {
    boolean existsByNickname(Nickname nickname);
    boolean existsByPhoneNumber(PhoneNumber phoneNumber);
}
