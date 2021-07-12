package com.nesty.chebit.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String pwd;
    //private List<Habit> habits;

}
