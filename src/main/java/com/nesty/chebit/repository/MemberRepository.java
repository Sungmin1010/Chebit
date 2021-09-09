package com.nesty.chebit.repository;

import com.nesty.chebit.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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

    public List<Member> findByEmail(String email){
        return em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList();

    }

    public Member findOneByEmail(String email){
        return em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getSingleResult();

    }

    public Long countByEmail(String email){
        return em.createQuery("select count(m) from Member m where m.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public void delete(Member member){
        em.remove(member);
    }


}
