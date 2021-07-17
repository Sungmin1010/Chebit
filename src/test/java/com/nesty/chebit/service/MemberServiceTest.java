package com.nesty.chebit.service;

import com.nesty.chebit.web.dto.MemberJoinRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    
    @Test
    public void 중복회원가입() throws Exception {
        //given
        MemberJoinRequestDto memberJoinRequestDto1 = new MemberJoinRequestDto("jenny", "lmsgsm1@gmail.com", "1234");
        MemberJoinRequestDto memberJoinRequestDto2 = new MemberJoinRequestDto("jenny2", "lmsgsm1@gmail.com", "1234");
        memberService.join(memberJoinRequestDto1);
        //when


        //then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            memberService.join(memberJoinRequestDto2);
        });

    
    }

}