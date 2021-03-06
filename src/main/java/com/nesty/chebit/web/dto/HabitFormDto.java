package com.nesty.chebit.web.dto;

import com.nesty.chebit.domain.Habit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter @NoArgsConstructor @ToString
@Setter
public class HabitFormDto {

    @NotEmpty
    private String title;
    @NotEmpty
    private String sdate;
    private String memo;

    /**
     * Entity -> DTO
     */
    public HabitFormDto(Habit habit) {
        this.title = habit.getTitle();
        this.sdate = habit.getStartDate().toString();
        this.memo = habit.getMemo();
    }
}
