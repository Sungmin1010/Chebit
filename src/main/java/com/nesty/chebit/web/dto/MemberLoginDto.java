package com.nesty.chebit.web.dto;


import com.nesty.chebit.domain.Member;
import lombok.*;

@Getter @Setter @ToString @NoArgsConstructor
public class MemberLoginDto {

    private String email;
    private String pwd;

    public MemberLoginDto(Member member) {
        this.email = member.getEmail();
        this.pwd = member.getPwd();
    }

    public MemberLoginDto(String email, String pwd) {
        this.email = email;
        this.pwd = pwd;
    }
}
