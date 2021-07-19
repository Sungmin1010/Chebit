package com.nesty.chebit.repository;

import com.nesty.chebit.domain.Habit;
import com.nesty.chebit.domain.Member;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository
public class HabitRepository {

    @PersistenceContext
    private EntityManager em;

    public Habit findOneHabit(Long id){
        Habit findHabit = em.find(Habit.class, id);
        return findHabit;
    }


    public Long saveHabit(Habit habit){
        em.persist(habit);
        return habit.getId();
    }

    public List<Habit> findByMember(Member member){
        return em.createQuery("select h from Habit h where h.member = :member")
                .setParameter("member", member)
                .getResultList();
    }

    public List<Habit> findByMemberWithStartDate(Member member, LocalDate startDate){
        return em.createQuery("select h from Habit h where h.member = :member and h.startDate <= :startDate")
                .setParameter("member", member)
                .setParameter("startDate", startDate)
                .getResultList();
    }

    public List<Habit> findHabitWithTodayRecord(Member member, LocalDate recDate){
        return em.createQuery("select h from Habit h left join h.records r where h.member = :member ")
                .setParameter("member", member)
                //.setParameter("recDate", recDate)
                .getResultList();
    }






}
