package TIL.example;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Repository
public class TeamRepository {

	final EntityManager em;

	public TeamRepository(EntityManager em) {
		this.em = em;
	}

	void save(Team team) {
		em.persist(team);
	}
}
