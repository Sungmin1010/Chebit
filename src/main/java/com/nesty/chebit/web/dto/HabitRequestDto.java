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

    //ENTITY -> DTO
    public HabitRequestDto(Habit habit, int todayRecordCnt){
        this.title = habit.getTitle();
        this.id = habit.getId();
        if(todayRecordCnt > 0){
            System.out.println("aaaa");
            this.isChecked = true;
        }else{
            this.isChecked = false;
        }
    }


}
