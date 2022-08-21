package STUDY.TIL.jpa;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor
public class Place {
    @Id
    String name;
    String location;

    @ManyToOne
    Brand brand;

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @Builder
    public Place(String name, String location) {
        this.name = name;
        this.location = location;
    }

}
