package TIL.example;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

	EntityManager em;

	public List<Member> readMemberWithFetchJoin() {
		Query query = em.createQuery(
			"SELECT m from Member m join fetch m.team");
		List resultList = query.getResultList();
		return resultList;
	}

	public List<Member> readMemberWithJoin() {
		Query query = em.createQuery(
			"SELECT m from Member m join m.team");
		List resultList = query.getResultList();
		return resultList;
	}

	public List<Team> readTeamWithFetchJoin() {
		Query query = em.createQuery(
			"SELECT m.team from Member m join fetch m.team");
		List resultList = query.getResultList();
		return resultList;
	}

	public List<Team> readTeamWithJoin() {
		Query query = em.createQuery(
			"SELECT m.team from Member m join m.team");
		List resultList = query.getResultList();
		return resultList;
	}

	void save(Member member) {
		em.persist(member);
	}
}
