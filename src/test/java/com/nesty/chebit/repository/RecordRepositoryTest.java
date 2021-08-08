package com.nesty.chebit.repository;

import com.nesty.chebit.domain.Habit;
import com.nesty.chebit.domain.Member;
import com.nesty.chebit.domain.Record;
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
class RecordRepositoryTest {

    @Autowired
    public RecordRepository recordRepository;
    @Autowired
    public MemberRepository memberRepository;
    @Autowired
    public HabitRepository habitRepository;
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
    public void testFind() throws Exception {
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
        System.out.println("==========================");
        em.clear();

        System.out.println("==========================");

        List<Record> findRecord = em.createQuery("select r from Record r where r.habit = :habit and r.recDate = :today", Record.class)
                .setParameter("habit", habit1)
                .setParameter("today", today)
                .getResultList();
        System.out.println(findRecord.size());
        //int habit1TodayRec = recordRepository.findTodayRecord(habit1, today, member);
        System.out.println("==========================");
       // System.out.println("result : " + habit1TodayRec);
        //System.out.println(habit1.getRecords().size());



    }

    @Test
    @Transactional
    @Rollback(false)
    public void 모든_기록_삭제_테스트() throws Exception {
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
        em.flush();
        em.clear();
        System.out.println("=============1=============");

        //when
        Habit findHabit = habitRepository.findOneHabit(habit1.getId());
        System.out.println("=============2=============");

        findHabit.getRecords().stream().forEach(
                o -> {
                    //o.removeRecord();
                    em.remove(o);
                }
        );

        em.remove(findHabit);
        //em.remove(findHabit.getRecords());


        System.out.println("=============3=============");
        em.flush();
        em.clear();
    }

    @Test
    @Rollback(false)
    @Transactional
    public void findWeeklyRecordsTest() throws Exception {
        //given
        LocalDate date = LocalDate.of(2021,8,1);
        Member member = getMember("jenny", "lm@naver.com", "1234");
        Habit habit1 = Habit.createHabit("습관1", "메모", date, member );
        habitRepository.saveHabit(habit1);
        for(int i=0 ; i<7; i++){
            LocalDate day = LocalDate.of(2021, 8, 1+i);
            Record record = Record.createNewRecord(habit1, day);
            recordRepository.save(record);
        }


        //when

        List<Record> weeklyRecords = recordRepository.findWeeklyRecords(habit1, date, date.plusDays(6));


        //then
        Assertions.assertThat(weeklyRecords.size()).isEqualTo(7);


    }

    @Test
    @Rollback(false)
    @Transactional
    public void removeRecordTest() throws Exception {
        //given
        LocalDate date = LocalDate.of(2021,8,1);
        Member member = getMember("jenny", "lm@naver.com", "1234");
        Habit habit1 = Habit.createHabit("습관1", "메모", date, member );
        habitRepository.saveHabit(habit1);
        LocalDate day = LocalDate.of(2021, 8, 1);
        Record record = Record.createNewRecord(habit1, day);
        recordRepository.save(record);

        //when
        int a = recordRepository.removeRecord(habit1.getId(), record.getRecDate());
        em.flush();
        em.clear();
        System.out.println("=======================================");
        Record findRecord = recordRepository.find(record.getId());

        //then
        Assertions.assertThat(findRecord).isNull();
    }

}