package com.example.datajpa.repository;

import com.example.datajpa.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberJpaRepositoryTest {

    @Autowired MemberJpaRepository memberJpaRepository;

    @Test @Transactional @Rollback(false)
    public void testMember() {
//        Member member = new Member();
//        member.setUsername("member1");
//
//        Member savedMember = memberJpaRepository.save(member);
//        Member findMember = memberJpaRepository.find(savedMember.getId());
//
//        assertThat(findMember.getId()).isEqualTo(member.getId());
//        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
//        assertThat(findMember).isEqualTo(member);
    }
}