package com.nesty.chebit.web.dto;

import com.nesty.chebit.domain.Habit;
import com.nesty.chebit.domain.Record;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class WeeklyHabitDto {

    private Long id;

    private String title;

    private List<WeeklyRecordDto> records;  //recDate, isChecked 7개씩



    /**
     * Entity -> Dto
     */
    public WeeklyHabitDto(Habit habit) {
        this.id = habit.getId();
        this.title = habit.getTitle();
        records = new ArrayList<>();
    }

    public void setWeeklyRecordDto(LocalDate sdate, List<WeeklyRecordDto> findRecord){
        for(int i=0 ;i<7; i++){
            LocalDate recDate = sdate.plusDays(i);
            records.add(i, new WeeklyRecordDto(recDate));
        }

        for(WeeklyRecordDto dto:findRecord){
            int dayOfWeek = dto.getRecDate().getDayOfWeek().getValue();
            records.set(dayOfWeek-1, dto);
        }
    }


}
