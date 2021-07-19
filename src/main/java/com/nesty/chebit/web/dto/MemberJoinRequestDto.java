package com.nesty.chebit.web.dto;

import com.nesty.chebit.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberJoinRequestDto {

    private final String name;
    private final String email;
    private final String pwd;

    public Member toEntity(){
        return Member.createMember(name, email, pwd);
    }

}
