package com.example.datajpa.entity;

import com.example.datajpa.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest @Transactional @Rollback(false)
class MemberTest {

    @PersistenceContext EntityManager em;
    @Autowired MemberRepository memberRepository;

    @Test
    public void testEntity() {
        Team team1 = new Team("team1");
        Team team2 = new Team("team2");

        em.persist(team1);
        em.persist(team2);

        Member member1 = new Member("member1", 10, team1);
        Member member2 = new Member("member2", 20, team1);
        Member member3 = new Member("member3", 30, team2);
        Member member4 = new Member("member4", 40, team2);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        List<Member> members = em.createQuery("select m from Member m", Member.class)
                                 .getResultList();

        em.flush();
        em.clear();

        for (Member member : members) {
            System.out.println("member = " + member);
            System.out.println("member.team = " + member.getTeam());
        }
    }

    @Test
    public void JpaEventBaseEntity() throws Exception {
        Member member = new Member("member1");
        memberRepository.save(member);

        Thread.sleep(1000);

        member.setUsername("memberA");

        em.flush();
        em.clear();

        Member findMember = memberRepository.findById(member.getId()).get();
        System.out.println("findMember = " + findMember);
        System.out.println(findMember.getCreatedDate());
        System.out.println(findMember.getLastModifiedDate());
        System.out.println(findMember.getCreatedBy());
        System.out.println(findMember.getLastModifiedBy());
    }
}