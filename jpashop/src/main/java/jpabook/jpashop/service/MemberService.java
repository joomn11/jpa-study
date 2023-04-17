package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // 생성자 주입, 변경 불가능한 안전한 객체 생성 가능
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 체크
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) { // 실무에서는 동시성 문제로 무의미한 코드가 될 수 있음, DB에 유니크 제약 조건을 거는것이 더 안전
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String newName) {
        Member findMember = memberRepository.findOne(id);
        findMember.setName(newName);
    }
}
