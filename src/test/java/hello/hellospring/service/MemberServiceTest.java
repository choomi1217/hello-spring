package hello.hellospring.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberServiceTest {

    MemoryMemberRepository repository;
    MemberService memberService;

    @BeforeEach
    public void beforeEach(){
        repository = new MemoryMemberRepository();
        memberService = new MemberService(repository);
    }

    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @DisplayName("회원 가입")
    @Test
    void join() {
        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long saveId = memberService.join(member);

        //then
        Member result = memberService.fineOne(saveId).get();
        assertThat(member.getName()).isEqualTo(result.getName());
    }

    @DisplayName("회원 중복 테스트")
    @Test
    void join_duplicate_exception() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);

        //then
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }

    @DisplayName("회원 찾기")
    @Test
    void fineOne() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        //when
        memberService.join(member1);
        Member findMember = memberService.fineOne(1L).get();

        //then
        assertThat(findMember.getName()).isEqualTo(member1.getName());
    }

}