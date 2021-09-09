package com.nesty.chebit.service;

import com.nesty.chebit.domain.Member;
import com.nesty.chebit.repository.MemberRepository;
import com.nesty.chebit.web.dto.MemberJoinRequestDto;


import com.nesty.chebit.web.dto.MemberLoginDto;
import com.nesty.chebit.web.dto.MemberSessionDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    @Test
    public void testJoin() throws Exception {
        //given
        MemberJoinRequestDto memberJoinRequestDto1 = new MemberJoinRequestDto("jenny", "lmsgsm1@gmail.com", "1234");
        when(memberRepository.countByEmail(any())).thenReturn(0L);
        when(memberRepository.save(any())).thenReturn(1L);
        //when
        Long result = memberService.join(memberJoinRequestDto1);
        //then
        Assertions.assertThat(result).isEqualTo(1L);

    }
    
    @Test
    public void testJoin_중복회원가입() throws Exception {
        //given
        MemberJoinRequestDto memberJoinRequestDto1 = new MemberJoinRequestDto("jenny", "lmsgsm1@gmail.com", "1234");

        //when
        when(memberRepository.countByEmail(any())).thenReturn(1L);

        //then
        Assertions.assertThatIllegalStateException().isThrownBy(
                () -> memberService.join(memberJoinRequestDto1)
        ).withMessageContaining("존재하는 이메일");

    }

    @Test
    public void testFindId() throws Exception {
        //given
        String email = "lm@mail.com";
        Member fakeMember = Member.createMember(1L, "jenny", email, "1234");
        List<Member> list = new ArrayList<>();
        list.add(fakeMember);
        when(memberRepository.findOneByEmail(email)).thenReturn(fakeMember);

        //when
        Long id = memberService.findId(email);

        //then
        Assertions.assertThat(id).isEqualTo(fakeMember.getId());

    }

    @Test
    public void testLogin_로그인성공() throws Exception {
        //given
        MemberLoginDto dto = new MemberLoginDto("lm@mail.com", "1234");
        Member fakeMember = Member.createMember(1L, "jenny", "lm@mail.com", "1234");
        List<Member> list = new ArrayList<>();
        list.add(fakeMember);
        when(memberRepository.findByEmail(dto.getEmail())).thenReturn(list);
        //when
        MemberSessionDto sessionDto = memberService.login(dto);
        //then

        Assertions.assertThat(sessionDto).isNotNull();
        Assertions.assertThat(sessionDto.getId()).isEqualTo(fakeMember.getId());
        Assertions.assertThat(sessionDto.getEmail()).isEqualTo(fakeMember.getEmail());
        Assertions.assertThat(sessionDto.getName()).isEqualTo(fakeMember.getName());

    }

    @Test
    public void testLogin_비밀번호다름() throws Exception {
        //given
        MemberLoginDto dto = new MemberLoginDto("lm@mail.com", "0000");
        Member fakeMember = Member.createMember(1L, "jenny", "lm@mail.com", "1234");
        List<Member> list = new ArrayList<>();
        list.add(fakeMember);
        when(memberRepository.findByEmail(dto.getEmail())).thenReturn(list);

        //when
        MemberSessionDto sessionDto = memberService.login(dto);
        //then
        Assertions.assertThat(sessionDto.getId()).isNull();
    }

    @Test
    public void testLogin_존재하지않는멤버() throws Exception {
        //given
        MemberLoginDto dto = new MemberLoginDto("lm@mail.com", "0000");
        List<Member> list = new ArrayList<>();
        when(memberRepository.findByEmail(dto.getEmail())).thenReturn(list);

        //when
        MemberSessionDto sessionDto = memberService.login(dto);

        //then
        Assertions.assertThat(sessionDto.getId()).isNull();

    }

}