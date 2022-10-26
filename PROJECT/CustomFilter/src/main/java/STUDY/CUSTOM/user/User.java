package STUDY.CUSTOM.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String password;
    private Role role;

    public void setRole(Role role) {
        this.role = role;
    }

    @Builder
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
