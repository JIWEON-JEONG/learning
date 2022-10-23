package TIL.jpa;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Brand {
    @Id
    @GeneratedValue
    Long id;
    String name;
    String description;

    @Builder
    public Brand(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
