package jpabook.jpashop.repository;

import java.util.List;
import javax.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

//    @PersistenceContext // 스프링 데이터 JPA를 사용하면 @Autowired로 가능
//    private EntityManager em;

    private final EntityManager em;

    /**
     * command 와 액션을 분리해라
     * 리턴시 member를 넘기지 않고 id를 넘김
     * @param member
     * @return
     */
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name=:name", Member.class)
                 .setParameter("name", name)
                 .getResultList();
    }
}
