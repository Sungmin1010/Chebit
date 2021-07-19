package com.nesty.chebit.repository;

import com.nesty.chebit.domain.Habit;
import com.nesty.chebit.domain.Member;
import com.nesty.chebit.domain.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecordRepository {


    private final  EntityManager em;

    public Long save(Record record){
        em.persist(record);
        return record.getId();
    }

    public Record find(Long recordId){
        Record record = em.find(Record.class, recordId);
        return record;
    }

    public List<Record> findTodayRecord(Habit habit, LocalDate today){
        return em.createQuery("select r from Record r join r.habit h " +
                "where h = :habit " +
                "and r.recDate = :today", Record.class)
                .setParameter("habit", habit)
                .setParameter("today", today)
                .getResultList();
    }

}
