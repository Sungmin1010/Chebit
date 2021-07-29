package com.nesty.chebit.web.dto;

import com.nesty.chebit.domain.Habit;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HabitDto {

    private Long id;
    private String title;
    private String sdate;
    private String memo;


    /**
     * Entity -> DTO
     */
    public HabitDto(Habit habit) {
        this.id = habit.getId();
        this.title = habit.getTitle();
        this.sdate = habit.getStartDate().toString();
        this.memo = habit.getMemo();
    }
}
