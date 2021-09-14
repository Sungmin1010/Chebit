package com.nesty.chebit;

import com.nesty.chebit.domain.Habit;
import com.nesty.chebit.domain.Member;
import com.nesty.chebit.domain.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDate;

//@Component
@RequiredArgsConstructor
public class InitDB {
    private final InitService initService;

    //@PostConstruct
    public void init(){

        initService.dbInit1();
    }

    //@Component
    //@Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;

        public void dbInit1(){
            Member member = Member.createMember("jenny", "lm@mail.com", "1234");
            em.persist(member);

            Habit habit1 = Habit.createHabit("습관1", "메모11", LocalDate.now(), member);
            Habit habit2 = Habit.createHabit("습관2", "메모22", LocalDate.now().minusDays(1), member);
            em.persist(habit1);
            em.persist(habit2);

            Record record = Record.createNewRecord(habit1, LocalDate.now());
            Record record2 = Record.createNewRecord(habit1, LocalDate.now().plusDays(1));
            Record record3 = Record.createNewRecord(habit1, LocalDate.now().minusDays(1));
            em.persist(record);
            em.persist(record2);
            em.persist(record3);

            Record record4 = Record.createNewRecord(habit2, LocalDate.now().minusDays(1));
            em.persist(record4);
        }

    }
}
