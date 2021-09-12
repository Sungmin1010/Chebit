package com.nesty.chebit.repository;

import com.nesty.chebit.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Rollback(false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em ;

    private Member insertMemberToDB(String name, String email, String pw){
        System.out.println("--------------INSERT MEMBER---------------");
        Member member = Member.createMember(name, email, pw);
        em.persist(member);
        em.flush();
        em.clear();
        System.out.println("-------------- Flush & Clear --------------");
        return member;
    }
    
    @Test
    @Transactional
    public void testSaveAndFind() throws Exception {
        //given
        Member member = Member.createMember("jenny", "test@mail.com", "1234");

        //when
        Long id = memberRepository.save(member);

        em.flush();
        em.clear();
        Member findMember = memberRepository.findOneMember(id);
        System.out.println("---------------- Flush & Clear ------------------");
        //then
        Assertions.assertThat(id).isEqualTo(member.getId());
        Assertions.assertThat(findMember)
                .usingRecursiveComparison()
                .isEqualTo(member);

    
    }

    
    @Test
    @Transactional
    public void testFindByEmail() throws Exception {
        //given
        //db에 특정인 데이터 저장되어 있는 상태
        String name = "kim";
        String email = "kim@mail.com";
        String pw = "1234";
        insertMemberToDB(name, email, pw);

        //when
        List<Member> findMembers = memberRepository.findByEmail(email);

        //then
        Assertions.assertThat(findMembers.size()).isEqualTo(1);
        Assertions.assertThat(findMembers.get(0).getEmail()).isEqualTo(email);
    
    }

    @Test
    public void testFindByEmail_해당되는데이터없는경우() throws Exception {
        //given
        //db에 해당되는 이메일을 가진 멤버가 없는 경우
        String email = "jung@mail.com";

        //when
        List<Member> findMembers = memberRepository.findByEmail(email);

        //then
        Assertions.assertThat(findMembers.size()).isEqualTo(0);
        Assertions.assertThat(findMembers).isEmpty();

    }
    
    @Test
    @Transactional
    public void testFindOneByEmail() throws Exception {
        //given
        String name = "kim";
        String email = "kim@mail.com";
        String pw = "1234";
        Member saveMember = insertMemberToDB(name, email, pw);

        //when
        Member findMember = memberRepository.findOneByEmail(email);


        //then
        Assertions.assertThat(findMember)
                .usingRecursiveComparison()
                .isEqualTo(saveMember);
    
    }

    @Test
    public void testFindOneByEmail_해당되는데이터없다() throws Exception {
        //given
        //db에 해당되는 이메일을 가진 멤버가 없는 경우
        String email = "jung@mail.com";

        //when
        Member findMember = memberRepository.findOneByEmail(email);


        //then
        Assertions.assertThat(findMember).isNull();

    }
    
    @Test
    @Transactional
    public void testCountByEmail() throws Exception {
        //given
        String name = "kim";
        String email = "kim@mail.com";
        String pw = "1234";
        Member saveMember = insertMemberToDB(name, email, pw);
        
        
        //when
        Long countResult = memberRepository.countByEmail(email);

        //then
        Assertions.assertThat(countResult).isEqualTo(1L);
    
    }

    @Test
    public void testCountByEmail_해당되는데이터없다() throws Exception {
        //given
        String email = "kim@mail.com";

        //when
        Long countResult = memberRepository.countByEmail(email);

        //then
        Assertions.assertThat(countResult).isEqualTo(0L);

    }
    
    @Test
    @Transactional
    public void testDelete() throws Exception {
        //given
        String name = "kim";
        String email = "kim@mail.com";
        String pw = "1234";
        Member saveMember = insertMemberToDB(name, email, pw);
        
        //when
        Member findMember = memberRepository.findOneMember(saveMember.getId());
        memberRepository.delete(findMember);
        em.flush();
        em.clear();
        
        //then
        Long aLong = memberRepository.countByEmail(email);
        Assertions.assertThat(aLong).isEqualTo(0L);

    }


}