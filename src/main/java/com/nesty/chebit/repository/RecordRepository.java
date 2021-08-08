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

    public void remove(Record record){
        em.remove(record);

    }

    public int removeAllRecords(Habit habit){
        return em.createQuery("delete from Record r where r.habit = :habit")
                .setParameter("habit", habit)
                .executeUpdate();
    }

    public List<Record> findWeeklyRecords(Habit habit, LocalDate sdate, LocalDate edate){
        return em.createQuery("select r from Record r " +
                "where r.recDate >= :sdate " +
                "and r.recDate <= :edate " +
                "and r.habit = :habit ", Record.class)
                .setParameter("sdate", sdate)
                .setParameter("edate", edate)
                .setParameter("habit", habit)
                .getResultList();
    }

    public int removeRecord(Long habitId, LocalDate recDate){

//        em.remove(record);
        return em.createQuery("delete from Record r where r.habit.id = :habitId and r.recDate = :recDate")
                .setParameter("habitId", habitId)
                .setParameter("recDate", recDate)
                .executeUpdate();
    }

}
