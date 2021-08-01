package com.nesty.chebit.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    private String email;

    private String pwd;

    @OneToMany(mappedBy = "member")
    private List<Habit> habits = new ArrayList<>();

    /**
     * 상태변경 메서드
     */


    /**
     * 생성 메서드
     */
    public static Member createMember(String name, String email, String pwd){
        Member member = new Member();
        member.name = name;
        member.email = email;
        member.pwd = pwd;
        return member;

    }



}
