package com.nesty.chebit.web.dto;


import com.nesty.chebit.domain.Record;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class WeeklyRecordDto {

    private LocalDate recDate;

    private boolean isChecked;

    public WeeklyRecordDto(LocalDate recDate) {
        this.recDate = recDate;
        this.isChecked = false;
    }

    public WeeklyRecordDto(Record record) {
        this.recDate = record.getRecDate();
        this.isChecked = true;
    }
}
