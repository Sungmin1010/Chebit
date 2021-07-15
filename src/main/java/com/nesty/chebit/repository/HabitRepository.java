package com.nesty.chebit.repository;

import com.nesty.chebit.domain.Habit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HabitRepository {

    private final EntityManager em;

    private Habit findOneHabit(Long id){
        Habit findHabit = em.find(Habit.class, id);
        return findHabit;
    }

    private void saveHabit(Habit habit){
        em.persist(habit);
    }

    private List<Habit> findHabitList(Long memberId){
        return em.createQuery("select h from Habit h where h.member = :memberId")
                .setParameter("memberId", memberId)
                .getResultList();
    }


}
