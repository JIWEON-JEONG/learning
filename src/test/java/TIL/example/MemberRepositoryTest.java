package TIL.example;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	TeamRepository teamRepository;

	@BeforeEach
	void init() {
		Team teamJohn = new Team("John");
		Team teamDiger = new Team("Diger");
		Member Diger = new Member("Diger Kim");
		Member John = new Member("John Jeong");

		teamRepository.save(teamJohn);
		teamRepository.save(teamDiger);

		Diger.setTeam(teamDiger);
		John.setTeam(teamJohn);

		memberRepository.save(Diger);
		memberRepository.save(John);
	}

	@Test
	void readMemberWithFetchJoin() {
		System.out.println("1----------------------------");
		List<Member> members = memberRepository.readMemberWithFetchJoin();
		Long id = members.get(0).team.id;
		System.out.println("1----------------------------");
	}

	@Test
	void readMemberWithJoin() {
		System.out.println("2----------------------------");
		List<Member> members = memberRepository.readMemberWithJoin();
		Long id = members.get(0).team.id;
		System.out.println("2----------------------------");
	}

	@Test
	void readTeamWithFetchJoin() {
		System.out.println("3----------------------------");
		List<Team> teams = memberRepository.readTeamWithFetchJoin();
		String name = teams.get(0).name;
		System.out.println("3----------------------------");
	}

	@Test
	void readTeamWithJoin() {
		System.out.println("4----------------------------");
		List<Team> teams = memberRepository.readTeamWithJoin();
		String name = teams.get(0).name;
		System.out.println("4----------------------------");
	}
}