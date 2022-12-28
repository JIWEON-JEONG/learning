package TIL.example;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.transaction.annotation.Transactional;

import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	String name;

	public Team(String name) {
		this.name = name;
	}
}
