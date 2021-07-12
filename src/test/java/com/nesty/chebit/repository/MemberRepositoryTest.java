package com.nesty.chebit.repository;

import com.nesty.chebit.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testMember() throws Exception {
        //given
        Member member = new Member();
        member.setName("jenny");
        member.setEmail("lmsgsm1@gmail.com");
        member.setPwd("1234");
        
        //when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.findOneMember(saveId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(saveId);
        Assertions.assertThat(findMember.getName()).isEqualTo("jenny");
        Assertions.assertThat(findMember).isEqualTo(member);
    
    }

}