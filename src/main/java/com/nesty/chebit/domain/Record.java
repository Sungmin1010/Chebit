package com.nesty.chebit.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Record {

    @Id @GeneratedValue
    @Column(name = "RECORD_ID")
    private Long id;


    private LocalDateTime recDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Habit habit;

    /**
     * 생성 메서드
     */
    public static Record createNewRecord(Habit habit, LocalDateTime recDate){
        Record record = new Record();
        record.recDate = recDate;
        record.habit = habit;
        return record;
    }


    /**
     * 상태변경메서드
     */








}
