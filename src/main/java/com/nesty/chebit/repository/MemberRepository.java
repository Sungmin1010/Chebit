package com.nesty.chebit.repository;

import com.nesty.chebit.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Member findOneMember(Long id){
        return em.find(Member.class, id);

    }


}
