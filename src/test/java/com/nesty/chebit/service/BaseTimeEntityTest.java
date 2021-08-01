package com.nesty.chebit.service;

import com.nesty.chebit.domain.Member;
import com.nesty.chebit.web.dto.MemberJoinRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@SpringBootTest
public class BaseTimeEntityTest {

    @Autowired
    MemberService memberService;
    @Autowired
    EntityManager em;

    @Test
    public void baseTimeEntity_등록시간_테스트() throws Exception {
        //given
        String email = "lmsgsm1@gmail.com";
        MemberJoinRequestDto memberJoinRequestDto1 = new MemberJoinRequestDto("jenny", email, "1234");
        LocalDateTime today = LocalDateTime.now();

        //when
        memberService.join(memberJoinRequestDto1);
        Long findId = memberService.findId(email);
        Member findMember = em.find(Member.class, findId);

        //then
        Assertions.assertThat(findMember.getCreatedDate()).isAfter(today);
        Assertions.assertThat(findMember.getModifiedDate()).isAfter(today.toString());


    }
}
