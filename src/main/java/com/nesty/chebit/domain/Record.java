package com.nesty.chebit.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Record {

    @Id @GeneratedValue
    @Column(name = "RECORD_ID")
    private Long id;


    private LocalDate recDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HABIT_ID")
    private Habit habit;


    private LocalDateTime createdDate;

    /**
     * 생성 메서드
     */
    public static Record createNewRecord(Habit habit, LocalDate recDate){
        Record record = new Record();
        record.recDate = recDate;
        //record.habit = habit;
        record.createdDate = LocalDateTime.now();
        record.setHabit(habit);
        return record;
    }


    /**
     * 상태변경메서드
     */

    /**
     * 연관관계 편의 메서드
     */
    private void setHabit(Habit habbit){
        this.habit = habbit;
        habbit.getRecords().add(this);
    }








}
