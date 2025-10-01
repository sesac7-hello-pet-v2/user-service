package hello.pet.userservice.domain.entity;

import hello.pet.userservice.domain.vo.Nickname;
import hello.pet.userservice.domain.vo.PhoneNumber;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_details")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(nullable = false)
    private String username;

    @Getter
    @Column(unique = true)
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "nickname_value"))
    private Nickname nickname;

    @Getter
    private String userProfileUrl;

    @Getter
    @Column(nullable = false)
    private String address;

    @Getter
    @Column(unique = true)
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "phone_value"))
    private PhoneNumber phoneNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void linkUser(User user) {
        this.user = user;
    }
}
