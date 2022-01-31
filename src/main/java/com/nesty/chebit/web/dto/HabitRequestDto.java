package com.nesty.chebit.web.dto;

import com.nesty.chebit.domain.Habit;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class HabitRequestDto {

    private String title;
    private Long id;
    private Boolean isChecked;
    private int consecDays;
    private String memo;

    //ENTITY -> DTO
    public HabitRequestDto(Habit habit, int todayRecordCnt, int consecDays){
        this.title = habit.getTitle();
        this.id = habit.getId();
        if(todayRecordCnt > 0){
            this.isChecked = true;
            this.consecDays = consecDays+1;
        }else{
            this.isChecked = false;
            this.consecDays = consecDays;
        }
        this.memo = habit.getMemo();

    }


}
