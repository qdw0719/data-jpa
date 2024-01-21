package com.example.datajpa.controller;

import com.example.datajpa.dto.MemberDTO;
import com.example.datajpa.entity.Member;
import com.example.datajpa.entity.Team;
import com.example.datajpa.repository.MemberRepository;
import com.example.datajpa.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    @GetMapping("/members")
    public Page<MemberDTO> MemberList(@PageableDefault(size = 5) Pageable pageable) {
        return memberRepository.findAll(pageable).map(MemberDTO::new);
    }

    @PostConstruct
    public void init() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        for (int i = 0; i < 100; i++) {
            int age = i;
            age = ++age;
            Team team;
            if (i < 50) {
                team = teamA;
            } else {
                team = teamB;
            }
            memberRepository.save(new Member("user" + age, age, team));
        }
    }
}
