package com.nesty.chebit.service;

import com.nesty.chebit.domain.Record;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import java.time.LocalDate;

@SpringBootTest
public class RecordServiceTest {

    @Autowired
    private RecordService recordService;
    @Autowired
    private EntityManager em;


    @Test
    @Rollback(false)
    public void 특정습관_오늘기록_추가() throws Exception {
        //given  addTodayRecord(Long habitId, LocalDate today)
        //습관id를 전달하면 해당 습관의 오늘 기록을 추가한다.
        //이미 기록이 있는지 확인해서 있으면 에러.. 없으면 추가
        Long habitId = 3L;
        LocalDate today = LocalDate.now();

        //when
        Long recordId = recordService.addTodayRecord(habitId, today);

        //then
        Record findRecord = em.find(Record.class, recordId);
        Assertions.assertThat(findRecord.getHabit().getId()).isEqualTo(habitId);
        Assertions.assertThat(findRecord.getRecDate()).isEqualTo(today);

    }
}
