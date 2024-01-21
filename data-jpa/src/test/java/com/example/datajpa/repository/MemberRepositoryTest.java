package com.example.datajpa.repository;

import com.example.datajpa.dto.MemberDTO;
import com.example.datajpa.dto.UsernameOnlyDTO;
import com.example.datajpa.entity.Member;
import com.example.datajpa.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Transactional @Rollback(false)
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;
    @Autowired EntityManager em;

    @Test
    public void testFindMemberDTO() {
        Member member1 = new Member("AAA");
        Member member2 = new Member("BBB");

        memberRepository.save(member1);
        memberRepository.save(member2);

        Team team1 = new Team("teamA");
        Team team2 = new Team("teamB");

        teamRepository.save(team1);
        teamRepository.save(team2);

        member1.setTeam(team1);
        member2.setTeam(team2);

        List<MemberDTO> memberDTO = memberRepository.findMemberDTO();
        for (MemberDTO dto : memberDTO) {
            System.out.println("team >> " + dto.getTeamname() + " / " + "userName >> " + dto.getUsername());
        }
    }

    @Test
    public void testFindByNames() {
        Member memberA = new Member("AAA");
        Member memberB = new Member("BBB");
        Member memberC = new Member("CCC");
        Member memberD = new Member("DDD");
        Member memberE = new Member("EEE");

        memberRepository.save(memberA);
        memberRepository.save(memberB);
        memberRepository.save(memberC);
        memberRepository.save(memberD);
        memberRepository.save(memberE);

        List<Member> members = memberRepository.findByNames(Arrays.asList("AAA", "CCC", "EEE"));
        for (Member member : members) {
            System.out.println(member.getUsername());
        }
    }

    @Test
    public void paging() {
        memberRepository.save(new Member("AAA", 10));
        memberRepository.save(new Member("BBB", 20));
        memberRepository.save(new Member("CCC", 10));
        memberRepository.save(new Member("DDD", 20));
        memberRepository.save(new Member("EEE", 10));

        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "username"));
        Page<Member> page = memberRepository.findByAge(age, pageRequest);

        List<Member> content = page.getContent();
        for (Member member : content) {
            System.out.println("member = " + member);
        }
        System.out.println("totalElement = " + page.getTotalElements());
        System.out.println("pageNo = " + page.getNumber());
        System.out.println("totalPage = " + page.getTotalPages());
        System.out.println("isFirst = " + page.isFirst());
        System.out.println("hasNext = " + page.hasNext());
        System.out.println("hasPrevious = " + page.hasPrevious());

        // entoty -> dto
        Page<MemberDTO> toMap = page.map(member -> new MemberDTO(member.getId(), member.getUsername(), null));

        PageRequest sliceRequest = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "username"));
        Slice<Member> slice = memberRepository.findSliceByAge(age, sliceRequest);

        List<Member> sliceContent = slice.getContent();
        for (Member member : sliceContent) {
            System.out.println("slice member = " + member);
        }
    }

    @Test
    public void bulkUpdate() {
        memberRepository.save(new Member("AAA", 21));
        memberRepository.save(new Member("BBB", 24));
        memberRepository.save(new Member("CCC", 32));
        memberRepository.save(new Member("DDD", 67));
        memberRepository.save(new Member("EEE", 43));
        memberRepository.save(new Member("FFF", 19));
        memberRepository.save(new Member("GGG", 14));
        memberRepository.save(new Member("HHH", 51));
        memberRepository.save(new Member("III", 5));
        memberRepository.save(new Member("JJJ", 8));
        memberRepository.save(new Member("KKK", 11));

        memberRepository.bulkAgePlus(10);

        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            System.out.println(member.getUsername() + " = " + member.getAge());
        }
    }

    @Test
    public void findMemberLazy() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member memberA = new Member("AAA", 29, teamA);
        Member memberB = new Member("BBB", 31, teamB);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            System.out.println("member = " + member.getUsername());
            System.out.println("member.team.getName = " + member.getTeam().getName());
            System.out.println("member.team = " + member.getTeam().getClass());
        }
    }

    @Test
    public void findMemberFetchJoin() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member memberA = new Member("AAA", 29, teamA);
        Member memberB = new Member("BBB", 31, teamB);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        List<Member> members = memberRepository.findMemberFetchJoinSelf();
        for (Member member : members) {
            System.out.println("member = " + member.getUsername());
            System.out.println("member.team.getName = " + member.getTeam().getName());
            System.out.println("member.team = " + member.getTeam().getClass());
        }
    }

    @Test
    public void findMemberEntityGraph() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member memberA = new Member("AAA", 29, teamA);
        Member memberB = new Member("BBB", 31, teamB);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            System.out.println("member = " + member.getUsername());
            System.out.println("member.team.getName = " + member.getTeam().getName());
            System.out.println("member.team = " + member.getTeam().getClass());
        }
    }

    @Test
    public void callCustom() {
        List<Member> custom = memberRepository.findMemberCustom();
    }

    @Test
    public void projections() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member memberA = new Member("AAA", 29, teamA);
        Member memberB = new Member("BBB", 31, teamB);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        List<UsernameOnly> usernames = memberRepository.findProjectionsByUsername("AAA", UsernameOnly.class);
        for (UsernameOnly username : usernames) {
            System.out.println("(UsernameOnly) username = " + username.getUsername());
        }

        List<UsernameOnlyDTO> usernamesDto = memberRepository.findProjectionsByUsername("AAA", UsernameOnlyDTO.class);
        for (UsernameOnlyDTO username : usernamesDto) {
            System.out.println("(UsernameOnlyDTO)username = " + username.getUsername());
        }

        List<NestedClosedProjections> nestedClosedProjections = memberRepository.findProjectionsByUsername("AAA", NestedClosedProjections.class);
        for (NestedClosedProjections nestedClosedProjection : nestedClosedProjections) {
            String username = nestedClosedProjection.getUsername();
            String teamName = nestedClosedProjection.getTeam().getName();
            System.out.println("nestedClosedProjections = (" + teamName + ")" + username);
        }
    }

    @Test
    public void useNativeQuery() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member memberA = new Member("AAA", 29, teamA);
        Member memberB = new Member("BBB", 31, teamB);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        Member member = memberRepository.useNativeQuery("AAA");
        System.out.println("member = " + member);
    }

    @Test
    public void useNativeQueryProjection() {
        Page<MemberProjection> page = memberRepository.useNativeProjection(PageRequest.of(0, 10));
        List<MemberProjection> content = page.getContent();
        for (MemberProjection memberProjection : content) {
            System.out.println("(" + memberProjection.getTeamName() + ")" + memberProjection.getUsername() + " " + memberProjection.getAge());
        }
    }
}