package com.example.demo.repository;

import com.example.demo.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {


    private final EntityManager em;

    public void save(Member member) {
        validateDuplicateMember(member);
        em.persist(member);
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = findByEmail(member.getEmail());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public Member findByEmail(String email) { return em.createQuery("select m from Member m where m.email = :email", Member.class)
            .setParameter("email", email)
            .getSingleResult();}
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.username = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
