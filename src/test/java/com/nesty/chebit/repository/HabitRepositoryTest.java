package com.nesty.chebit.repository;

import com.nesty.chebit.domain.Habit;
import com.nesty.chebit.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HabitRepositoryTest {

    @Autowired
    HabitRepository habitRepository;
    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    //@BeforeEach
    public Member saveMember() throws Exception {
        Member member = Member.createMember("jenny", "lmsgsm1@gmail.com", "1234");
        Long memberId = memberRepository.save(member);
        return member;
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testSaveHabit() throws Exception {
        //given
        Member member = saveMember();
        LocalDate startDate = LocalDate.now();
        Habit newHabbit = Habit.createHabit("new Habit", "memo", startDate, member);
        //em.flush();
        System.out.println("===================1================");
        //when
        Long habitId = habitRepository.saveHabit(newHabbit);
        //em.flush();
        System.out.println("===================2================");
        //then
        Habit findHabit = habitRepository.findOneHabit(habitId);
        System.out.println("===================3================");
        Assertions.assertThat(findHabit.getId()).isEqualTo(newHabbit.getId());
        Assertions.assertThat(findHabit).isEqualTo(newHabbit);

    }

    @Test @Transactional @Rollback(false)
    public void 연관관계_편의메서드_테스트() throws Exception {
        //given
        Member member = Member.createMember("jenny", "test", "1234");
        em.persist(member);

        System.out.println("member.getHabits().size() : " + member.getHabits().size());

        Habit habit = Habit.createHabit("title", "memo", LocalDate.now(), member);
        em.persist(habit);
        System.out.println("habit 객체가 갖고 있는 Member id : " + habit.getMember().getName());
        System.out.println("member 객체가 갖고 있는 habit 갯수 : " + member.getHabits().size());
        //when

        //then

    }



}