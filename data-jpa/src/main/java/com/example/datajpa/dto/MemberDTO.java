package com.example.datajpa.dto;

import com.example.datajpa.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

        private Long id;
        private String username;
        private String teamname;

        public MemberDTO(Member member) {
            this.id = member.getId();
            this.username = member.getUsername();
            this.teamname = member.getTeam().getName();
        }
}
