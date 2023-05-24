package study.datajpa.repository;


import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

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
}