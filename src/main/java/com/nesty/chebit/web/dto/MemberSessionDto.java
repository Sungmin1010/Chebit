package com.nesty.chebit.web.dto;

import com.nesty.chebit.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberSessionDto {

    private Long id;
    private String email;
    private String name;

    public MemberSessionDto(Member member) {
        id = member.getId();
        email = member.getEmail();
        name = member.getName();
    }

    public MemberSessionDto(String email){

    }

    public boolean isEmpty(){
        if(id == null) return true;
        if(email.isEmpty()) return true;
        if(name.isEmpty()) return true;
        return false;
    }


}
