package com.nesty.chebit.web.dto;

import com.nesty.chebit.exception.NotValidRecordDateException;
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
    private Long recordId;

    public void convertToLocalDate(){
        String[] split = inputDate.split("-");
        if(split.length < 3){
            throw new NotValidRecordDateException(
                    "올바른 날짜 형식이 아닙니다. ["+inputDate+"]"
            );
        }
        recDate = LocalDate.of(
                Integer.parseInt(split[0]),
                Integer.parseInt(split[1]),
                Integer.parseInt(split[2])
        );
    }
}
