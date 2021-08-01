package com.nesty.chebit.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Setter @Getter
@NoArgsConstructor
@ToString
public class RecordAddDto {

    private Long habitId;
    private String inputDate;  //yyyy-mm-dd
    private LocalDate recDate;

    public void convertToLocalDate(){
        String[] split = inputDate.split("-");
        recDate = LocalDate.of(
                Integer.parseInt(split[0]),
                Integer.parseInt(split[1]),
                Integer.parseInt(split[2])
        );
    }
}
