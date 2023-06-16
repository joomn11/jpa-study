package study.datajpa.repository;


import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    void testMember() {
        System.out.println("memberRepository : " + memberRepository.getClass());
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(savedMember.getId()).get();

        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member);
    }

    @Test
    void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        // single select test
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        Assertions.assertThat(findMember1).isEqualTo(member1);
        Assertions.assertThat(findMember2).isEqualTo(member2);

        // list select test
        List<Member> all = memberRepository.findAll();
        Assertions.assertThat(all.size()).isEqualTo(2);

        // count test
        long count = memberRepository.count();
        Assertions.assertThat(count).isEqualTo(2);

        // delete test
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        long deletedCount = memberRepository.count();
        Assertions.assertThat(deletedCount).isEqualTo(0);

    }

    @Test
    public void findByUsernameAndAgeGreaterThen() {
        Member user1 = new Member("User1", 10);
        Member user2 = new Member("User1", 20);
        memberRepository.save(user1);
        memberRepository.save(user2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("User1", 15);
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getAge()).isEqualTo(20);
        Assertions.assertThat(result.get(0).getUsername()).isEqualTo("User1");
    }

    @Test
    public void testNamedQuery() {
        Member user1 = new Member("User1", 10);
        Member user2 = new Member("User2", 20);
        memberRepository.save(user1);
        memberRepository.save(user2);

        List<Member> result = memberRepository.findByUsername("User1");

        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0)).isEqualTo(user1);
    }

    @Test
    public void testQuery() {
        Member user1 = new Member("User1", 10);
        Member user2 = new Member("User2", 20);
        memberRepository.save(user1);
        memberRepository.save(user2);

        List<Member> result = memberRepository.findMember("User1", 10);

        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0)).isEqualTo(user1);
    }

    @Test
    public void page() throws Exception {
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        // when
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Direction.DESC, "username"));
        Page<Member> page = memberRepository.findByAge(10, pageRequest);

        // then
        List<Member> content = page.getContent();
        Assertions.assertThat(content.size()).isEqualTo(3); // 조회 데이터
        Assertions.assertThat(page.getTotalElements()).isEqualTo(5); // 전체 데이터 수
        Assertions.assertThat(page.getNumber()).isEqualTo(0); // 페이지 번호
        Assertions.assertThat(page.getTotalPages()).isEqualTo(2); // 전체 페이지 수
        Assertions.assertThat(page.isFirst()).isTrue(); // 첫번째 페이지 인가?
        Assertions.assertThat(page.hasNext()).isTrue(); // 다음 페이지가 있나?
    }

    @Test
    public void bulkUpdate() throws Exception {
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 21));
        memberRepository.save(new Member("member5", 40));

        // when
        int resultCount = memberRepository.bulkAgePlus(20);

        // then
        Assertions.assertThat(resultCount).isEqualTo(3);
    }

    @Test
    public void findMemberLazy() throws Exception {
        // given
        // member1 -> teamA
        // member2 -> teamB
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        memberRepository.save(new Member("member1", 10, teamA));
        memberRepository.save(new Member("member2", 20, teamB));

        em.flush();
        em.clear();

        // when
        List<Member> members = memberRepository.findAll();

        // then
        for (Member member : members) {
            boolean initialized = Hibernate.isInitialized(member.getTeam());
            System.out.println("member is initialized : " + initialized);

            member.getTeam().getName();

            boolean loaded = em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(member.getTeam());
            System.out.println("member is loaded : " + loaded);
        }
    }

    @Test
    public void queryHint() throws Exception {
        // given
        memberRepository.save(new Member("member1", 10));
        em.flush();
        em.clear();

        // when
        Member member = memberRepository.findReadOnlyByUsername("member1");
        member.setUsername("member2");

        em.flush(); // update is not excuted !
    }

    @Test
    public void customImplTest() {
        List<Member> memberCustom = memberRepository.findMemberCustom();
    }
}