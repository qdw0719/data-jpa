package com.example.datajpa.repository;

import com.example.datajpa.dto.MemberDTO;
import com.example.datajpa.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    @Query("select new com.example.datajpa.dto.MemberDTO(m.id, m.username, t.name) from Member m inner join m.team t")
    List<MemberDTO> findMemberDTO();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    @Query(
            value = "select m from Member m left outer join m.team t",
            countQuery = "select count(m.username) from Member m"
    )
    Page<Member> findByAge(int age, Pageable pageable);

    Slice<Member> findSliceByAge(int age, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 10 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left outer join fetch m.team")
    List<Member> findMemberFetchJoinSelf();

    @Override @EntityGraph("Member.all")
    List<Member> findAll();

//    List<UsernameOnly> findProjectionsByUsername(@Param("username") String username);

//    List<UsernameOnlyDTO> findProjections2ByUsername(@Param("username") String username);

    <T> List<T> findProjectionsByUsername(@Param("username") String username, Class<T> type);

    @Query(value = "select * from member where username = :username", nativeQuery = true)
    Member useNativeQuery(@Param("username") String username);

    @Query(
        value = "select m.member_id as id, m.username, m.age, t.name as teamName " +
                "from member m left join team t",
        countQuery = "select count(*) from member",
        nativeQuery = true
    )
    Page<MemberProjection> useNativeProjection(Pageable pageable);
}
