package com.nesty.chebit.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Habit {

    @Id @GeneratedValue
    @Column(name = "HABIT_ID")
    private Long id;

    private String title;

    private LocalDateTime startDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String memo;

    /**
     * 생성 메서드
     */
    public static Habit createHabit(String title, String memo ,LocalDateTime startDate, Member member){
        Habit habit = new Habit();
        habit.title = title;
        habit.memo = memo;
        habit.startDate = startDate;
        habit.member = member;
        return habit;
    }


    /**
     * 상태변경 메서드
     */


    /**
     * 연관관계 편의 메서드
     */
    public void setMember(Member member){
        this.member = member;
        member.getHabits().add(this);
    }
}
