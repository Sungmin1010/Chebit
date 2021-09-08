package com.nesty.chebit.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nesty.chebit.exception.NotValidRecordDateException;
import com.nesty.chebit.service.RecordService;
import com.nesty.chebit.web.dto.MemberSessionDto;
import com.nesty.chebit.web.dto.RecordAddDto;
import com.nesty.chebit.web.dto.WeeklyRemoveRecordDto;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RecordController.class)
class RecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecordService recordService;

    private MemberSessionDto sessionDto = new MemberSessionDto(1L, "jenny", "lm@mail.com");


    @Test
    public void testSaveTodayRecord() throws Exception {
        //given
        //MemberSessionDto sessionDto = new MemberSessionDto(1L, "jenny", "lm@mail.com");
        LocalDate recordLocalDate = LocalDate.of(2021, 9, 5);
        Long habitId = 2L;
        Long recordId = 3L;
        RecordAddDto dto = new RecordAddDto();
        dto.setHabitId(habitId);
        dto.setInputDate(recordLocalDate.toString());
        when(recordService.addTodayRecord(any(), any())).thenReturn(recordId);

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/chebit/record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .sessionAttr("member", sessionDto));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.habitId").value(habitId))
                .andExpect(jsonPath("$.inputDate").value(recordLocalDate.toString()))
                .andExpect(jsonPath("$.recDate").isNotEmpty())
                .andExpect(jsonPath("$.recordId").value(recordId))
                .andDo(print());

    }

    @Test
    public void testSaveTodayRecord_잘못된inputDate형식() throws Exception {
        //given
        //MemberSessionDto sessionDto = new MemberSessionDto(1L, "jenny", "lm@mail.com");
        Long habitId = 2L;
        Long recordId = 3L;
        RecordAddDto dto = new RecordAddDto();
        dto.setHabitId(habitId);
        dto.setInputDate("2021-09");
        when(recordService.addTodayRecord(any(), any())).thenReturn(recordId);

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/chebit/record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .sessionAttr("member", sessionDto));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(model().attribute("message", containsString("올바른 날짜 형식이 아닙니다.")))
                .andExpect(jsonPath("$.habitId").doesNotHaveJsonPath())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotValidRecordDateException))
                .andDo(print());

    }

    @Test
    public void testRemoveRecord() throws Exception {
        //given
        when(recordService.removeRecord(any())).thenReturn(1);
        WeeklyRemoveRecordDto dto = new WeeklyRemoveRecordDto();
        dto.setHabitId(1L);
        dto.setRecDate("2021-09-09");

        //when
        ResultActions resultActions = mockMvc.perform(
                delete("/chebit/weekly/record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .sessionAttr("member", sessionDto)
        );

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(result -> assertEquals(result.getResponse().getContentAsString(), "1"))
                .andExpect(result -> assertNull(result.getResolvedException()))
                .andDo(print());

    }

    @Test
    public void testSaveRecord() throws Exception {
        //given
        LocalDate recordLocalDate = LocalDate.of(2021, 9, 5);
        Long habitId = 2L;
        Long recordId = 3L;
        WeeklyRemoveRecordDto dto = new WeeklyRemoveRecordDto(habitId, "2021-9-5");


        when(recordService.addTodayRecord(any(), any())).thenReturn(recordId);

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/chebit/weekly/record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .sessionAttr("member", sessionDto));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.habitId").value(habitId))
                .andExpect(jsonPath("$.recordId").isNotEmpty())
                .andExpect(jsonPath("$.recordId").value(recordId))
                .andExpect(result -> assertNull(result.getResolvedException()))
                .andDo(print());

    }
}