package hello.pet.userservice.domain.entity;

import hello.pet.userservice.domain.vo.Nickname;
import hello.pet.userservice.domain.vo.PhoneNumber;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_details")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "nickname_value"))
    private Nickname nickname;

    private String userProfileUrl;

    @Column(nullable = false)
    private String address;

    @Column(unique = true)
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "phone_value"))
    private PhoneNumber phoneNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
