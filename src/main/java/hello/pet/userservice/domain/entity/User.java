package hello.pet.userservice.domain.entity;

import hello.pet.userservice.domain.vo.Email;
import hello.pet.userservice.domain.vo.Password;
import hello.pet.userservice.domain.vo.UserActivation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(unique = true)
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "email_value"))
    private Email email;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "password_value"))
    private Password password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "activation_value"))
    private UserActivation activation;

    @Getter
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserDetail userDetail;

    public void linkUserDetail(UserDetail ud) {
        this.userDetail = ud;
        if (ud != null) {
            ud.linkUser(this);
        }
    }
}
