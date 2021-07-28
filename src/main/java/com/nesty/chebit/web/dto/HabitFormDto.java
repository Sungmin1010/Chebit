package com.nesty.chebit.web.dto;

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


}
