package com.nesty.chebit.web.dto;

import com.nesty.chebit.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class MemberJoinRequestDto {

    @NotNull
    private final String name;

    @NotNull
    private final String email;

    @NotNull
    private final String pwd;

    public Member toEntity(){
        return Member.createMember(name, email, pwd);
    }

}
