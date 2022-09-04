package STUDY.TIL.jpa;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor
@Service
public class Place {
    @Id
    @GeneratedValue
    Long id;
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
