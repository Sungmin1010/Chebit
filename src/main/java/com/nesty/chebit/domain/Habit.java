package com.nesty.chebit.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Habit extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "HABIT_ID")
    private Long id;

    private String title;

    private LocalDate startDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String memo;

    @OneToMany(mappedBy = "habit")
    private List<Record> records = new ArrayList<>();

    /**
     * 생성 메서드
     */
    public static Habit createHabit(String title, String memo ,LocalDate startDate, Member member){
        Habit habit = new Habit();
        habit.title = title;
        habit.memo = memo;
        habit.startDate = startDate;
        //habit.member = member;
        habit.setMember(member);
        return habit;
    }


    /**
     * 상태변경 메서드
     */
    public Long edit(String title, String memo){
        if(!this.title.equals(title)){
            this.title = title;
        }
        if(!this.memo.equals(memo)) this.memo = memo;

        return id;
    }



    /**
     * 연관관계 편의 메서드
     */
    private void setMember(Member member){
        this.member = member;
        member.getHabits().add(this);
    }
}
