package com.nesty.chebit.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

@Setter @Getter @ToString
public class WeeklyDateDto {

    private String dayOfWeek;
    private String date;
    private String color;

    public WeeklyDateDto(LocalDate date) {
        this.dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREA);
        this.date = date.format(DateTimeFormatter.ofPattern("MM/dd"));
        if(date.getDayOfWeek().getValue()==6){
            this.color = "blue";
        }else if(date.getDayOfWeek().getValue()==7){
            this.color = "red";
        }else{
            this.color = "default";
        }

    }


}
