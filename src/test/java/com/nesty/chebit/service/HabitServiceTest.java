package com.nesty.chebit.service;

import com.nesty.chebit.domain.Habit;
import com.nesty.chebit.domain.Member;
import com.nesty.chebit.domain.Record;
import com.nesty.chebit.repository.HabitRepository;
import com.nesty.chebit.repository.MemberRepository;
import com.nesty.chebit.repository.RecordRepository;
import com.nesty.chebit.web.dto.HabitFormDto;
import com.nesty.chebit.web.dto.HabitRequestDto;
import com.nesty.chebit.web.dto.WeeklyDateDto;
import com.nesty.chebit.web.dto.WeeklyHabitDto;
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

    @Test
    @Transactional
    @Rollback(false)
    public void 사용자의_모든습관_리스트_조회() throws Exception {
        //given
        LocalDate today = LocalDate.now();
        Member member = getMember("jenny", "lm@naver.com", "1234");
        Habit habit1 = Habit.createHabit("습관1", "메모", today.minusDays(1), member );
        Habit habit2 = Habit.createHabit("습관2", "메모2", today, member );
        Habit habit3 = Habit.createHabit("습관2", "메모2", today.plusDays(1), member );
        habitRepository.saveHabit(habit1);
        habitRepository.saveHabit(habit2);
        habitRepository.saveHabit(habit3);



        em.flush();
        em.clear();
        System.out.println("====================================================================");

        //when
        //List<HabitFormDto> allHabits = habitService.findAllHabits(member.getId());

        //then

       // Assertions.assertThat(allHabits.size()).isEqualTo(3);
        //Assertions.assertThat(member.getHabits().size()).isEqualTo(3);

    }

    @Test
    @Rollback(false)
    @Transactional
    public void 특정습관_삭제() throws Exception {
        //given
        LocalDate today = LocalDate.now();
        Member member = getMember("jenny", "lm@naver.com", "1234");
        Habit habit1 = Habit.createHabit("습관1", "메모", today.minusDays(1), member );
        habitRepository.saveHabit(habit1);
        Record rec1 = Record.createNewRecord(habit1, today.minusDays(2));
        Record rec2 = Record.createNewRecord(habit1, today.minusDays(1));
        Record rec3 = Record.createNewRecord(habit1, today);
        recordRepository.save(rec1);
        recordRepository.save(rec2);
        recordRepository.save(rec3);

        //when

        int rec = habitService.deleteHabitWithAllRecord(habit1.getId());

        //then
        Assertions.assertThat(rec).isEqualTo(habit1.getRecords().size());

    }
    @Test
    @Rollback(false)
    @Transactional
    public void 기록없는_습관_삭제() throws Exception {
        //given
        LocalDate today = LocalDate.now();
        Member member = getMember("jenny", "lm@naver.com", "1234");
        Habit habit1 = Habit.createHabit("습관1", "메모", today.minusDays(1), member );
        habitRepository.saveHabit(habit1);

        //when
        int rec = habitService.deleteHabitWithAllRecord(habit1.getId());

        //then
        Assertions.assertThat(rec).isEqualTo(0);

    }

    @Test
    @Transactional
    @Rollback(false)
    public void findHabitWithWeeklyRecordTest() throws Exception {
        //given
        LocalDate today = LocalDate.of(2021,7,29);
        Member member = getMember("jenny", "lm@naver.com", "1234");
        Habit habit1 = Habit.createHabit("습관1", "메모", today.minusDays(1), member );
        Habit habit2 = Habit.createHabit("습관1", "메모", today.minusDays(1), member );
        habitRepository.saveHabit(habit1);
        habitRepository.saveHabit(habit2);

        for(int i=0 ; i<7; i++){
            LocalDate day = LocalDate.of(2021, 8, 1+i);
            Record record = Record.createNewRecord(habit1, day);
            recordRepository.save(record);
        }
        LocalDate day = LocalDate.of(2021, 8, 1);
        Record record = Record.createNewRecord(habit2, day);
        recordRepository.save(record);

        em.flush();
        em.clear();
        System.out.println("=================================================================================");


        //when
        List<WeeklyHabitDto> habitWithWeeklyRecord = habitService.findHabitWithWeeklyRecord(member.getId(), LocalDate.now());


        //then
        //습관은 2개
        //습관 첫번째 기록은 7개
        Assertions.assertThat(habitWithWeeklyRecord.size()).isEqualTo(2);
        //습관 첫번째 habitWithWeeklyRecord.get(0)
        WeeklyHabitDto weeklyHabitDto1 = habitWithWeeklyRecord.get(0);
        Assertions.assertThat(weeklyHabitDto1.getRecords().size()).isEqualTo(7);
        Assertions.assertThat(weeklyHabitDto1.getRecords().get(0).getRecDate().toString()).isEqualTo("2021-08-02");

        WeeklyHabitDto weeklyHabitDto2 = habitWithWeeklyRecord.get(1);
        Assertions.assertThat(weeklyHabitDto2.getRecords().get(0).isChecked()).isEqualTo(false);

    }

    @Test
    @Rollback(false)
    @Transactional
    public void getWeeklyDateTest() throws Exception {
        //given
        LocalDate today = LocalDate.of(2021,8,6);


        //when
        List<WeeklyDateDto> dateList = habitService.getWeeklyDate(today);
        for(WeeklyDateDto d:dateList){
            System.out.println(d.toString());
        }

        //then
        Assertions.assertThat(dateList.size()).isEqualTo(7);
        //Assertions.assertThat(dateList.get(0).get

    }

}