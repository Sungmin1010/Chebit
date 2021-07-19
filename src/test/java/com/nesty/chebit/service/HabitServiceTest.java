package com.nesty.chebit.service;

import com.nesty.chebit.domain.Habit;
import com.nesty.chebit.domain.Member;
import com.nesty.chebit.domain.Record;
import com.nesty.chebit.repository.HabitRepository;
import com.nesty.chebit.repository.MemberRepository;
import com.nesty.chebit.repository.RecordRepository;
import com.nesty.chebit.web.dto.HabitRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;


@SpringBootTest
class HabitServiceTest {

    @Autowired
    HabitService habitService;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    HabitRepository habitRepository;
    @Autowired
    RecordRepository recordRepository;
    @Autowired
    EntityManager em;


    private Member getMember(String name, String email, String pwd){
        Member member = Member.createMember(name, email, pwd);
        Long memberId = memberRepository.save(member);
        return member;
    }

    @Test
    @Transactional
    @Rollback(false)
    public void 사용자의습관_오늘기록여부와_함께_조회() throws Exception {
        //given
        LocalDate today = LocalDate.now();
        Member member = getMember("jenny", "lm@naver.com", "1234");
        Habit habit1 = Habit.createHabit("습관1", "메모", today.minusDays(1), member );
        Habit habit2 = Habit.createHabit("습관2", "메모2", today.minusDays(1), member );
        habitRepository.saveHabit(habit1);
        habitRepository.saveHabit(habit2);
        Record habit1Record = Record.createNewRecord(habit1, today);
        Record habit2Record = Record.createNewRecord(habit2, today.minusDays(1));
        recordRepository.save(habit1Record);
        recordRepository.save(habit2Record);

        em.flush();
        em.clear();

        //when
        List<HabitRequestDto> dtoList = habitService.findHabits(member.getId());

        //then
        Assertions.assertThat(dtoList.get(0).getIsChecked()).isEqualTo(true);
        Assertions.assertThat(dtoList.get(1).getIsChecked()).isEqualTo(false);

    }

}