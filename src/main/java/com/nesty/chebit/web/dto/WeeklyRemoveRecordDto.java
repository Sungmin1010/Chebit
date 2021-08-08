package com.nesty.chebit.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @ToString
public class WeeklyRemoveRecordDto {

    private Long habitId;
    private String recDate;

    public LocalDate getRecDateToLocalDate(){
        String[] splitDate = recDate.split("-");

        return LocalDate.of(
                Integer.parseInt(splitDate[0])
                , Integer.parseInt(splitDate[1])
                , Integer.parseInt(splitDate[2])
        );
    }

    public WeeklyRemoveRecordDto(Long habitId, String recDate) {
        this.habitId = habitId;
        this.recDate = recDate;
    }
}
