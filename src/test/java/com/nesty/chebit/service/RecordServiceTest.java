package com.nesty.chebit.service;

import com.nesty.chebit.domain.Habit;
import com.nesty.chebit.domain.Member;
import com.nesty.chebit.domain.Record;

import com.nesty.chebit.repository.MemberRepository;
import com.nesty.chebit.repository.RecordRepository;
import com.nesty.chebit.web.dto.WeeklyRemoveRecordDto;
import org.apache.tomcat.jni.Local;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

@SpringBootTest
public class RecordServiceTest {

    @Autowired
    private RecordService recordService;
    @Autowired
    private EntityManager em;

    //@MockBean
    @Autowired
    private RecordRepository recordRepository;


    @Test
    @Rollback(false)
    public void 특정습관_오늘기록_추가() throws Exception {
        //given  addTodayRecord(Long habitId, LocalDate today)
        //습관id를 전달하면 해당 습관의 오늘 기록을 추가한다.
        //이미 기록이 있는지 확인해서 있으면 에러.. 없으면 추가
        Long habitId = 3L;
        LocalDate today = LocalDate.now();
        LocalDateTime todayDateTime = LocalDateTime.now();

        //when
        Long recordId = recordService.addTodayRecord(habitId, today);

        //then
        Record findRecord = em.find(Record.class, recordId);
        Assertions.assertThat(findRecord.getHabit().getId()).isEqualTo(habitId);
        Assertions.assertThat(findRecord.getRecDate()).isEqualTo(today);
        Assertions.assertThat(findRecord.getCreatedDate()).isAfter(todayDateTime);

    }
    @Test
    @Rollback(false)
    @Transactional
    public void removeRecordTest() throws Exception {
        //given

        //WeeklyRemoveRecordDto weeklyRemoveRecordDto = new WeeklyRemoveRecordDto(3L, "2021-08-07");
        //LocalDate recDateToLocalDate = weeklyRemoveRecordDto.getRecDateToLocalDate();
        //when(recordRepository.removeRecord(weeklyRemoveRecordDto.getHabitId(), recDateToLocalDate)).thenReturn(1);

        Member member = Member.createMember("nesty", "test@mail.com", "1234");
        em.persist(member);
        Habit habit = Habit.createHabit("습관1", "메모", LocalDate.now(), member);
        em.persist(habit);
        Record record = Record.createNewRecord(habit, LocalDate.now());
        Record record2 = Record.createNewRecord(habit, LocalDate.now().plusDays(1));
        em.persist(record);
        em.persist(record2);
        em.flush();
        em.clear();

        WeeklyRemoveRecordDto weeklyRemoveRecordDto = new WeeklyRemoveRecordDto(habit.getId(), record.getRecDate().toString());


        //when
        recordService.removeRecord(weeklyRemoveRecordDto);
        //recordRepository.count(3L)
        //then
        //Assertions.assertThat(recDateToLocalDate).isEqualTo(LocalDate.of(2021, 8, 7));
        Long recordCount = recordRepository.count(habit.getId());
        Assertions.assertThat(recordCount).isEqualTo(1);



    }


}
