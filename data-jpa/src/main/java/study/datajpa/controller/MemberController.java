package study.datajpa.controller;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members1/{id}")
    public String findMember1(@PathVariable("id") long id) { // curl -XGET 'http://localhost:8080/members/1'
        return memberRepository.findById(id).get().getUsername();
    }

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size = 5, sort = "username", direction = Direction.DESC) Pageable pageable) { // curl -XGET 'http://localhost:8080/members?page-0&size-3&sort=id,desc'
        Page<Member> page = memberRepository.findAll(pageable);
        Page<MemberDto> pageDto = page.map(MemberDto::new);
        return pageDto;
    }

    @PostConstruct
    public void init() {
        memberRepository.save(new Member("member1"));
    }

}
