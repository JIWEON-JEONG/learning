package TIL.example;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	Team team;

	public void setTeam(Team team) {
		this.team = team;
	}

	public Member(String name) {
		this.name = name;
	}
}
