package com.nesty.chebit.web;

import com.nesty.chebit.service.HabitService;
import com.nesty.chebit.web.dto.HabitDto;
import com.nesty.chebit.web.dto.HabitRequestDto;
import com.nesty.chebit.web.dto.MemberSessionDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = HabitController.class)
class HabitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HabitService habitService;

    private MemberSessionDto sessionDto = new MemberSessionDto(1L, "jenny", "lm@mail.com");


    @Test //서비스에서 리턴하는 습관이 없으면 어떡하나?
    public void testMainHabitList_한개이상습관리스트() throws Exception {
        //given
        //세션값이 주어져야한다.
        //서비스 목객체는 무조건 리스트 반환
        List<HabitRequestDto> todayHabitList = new ArrayList<>();
        when(habitService.findHabits(sessionDto.getId())).thenReturn(todayHabitList);

        //when
        ResultActions result = mockMvc.perform(
                get("/chebit/main")
                        .sessionAttr("member", sessionDto));

        //then
        //응답값 200
        //뷰 이름
        //모델 어트리뷰트 이름 및 타입?
        //화면에 노출되는 값
        result
                .andExpect(status().isOk())
                .andExpect(view().name("index2"))
                .andExpect(model().attribute("list", todayHabitList))
                .andDo(print());

    }

    @Test
    public void testHabitList() throws Exception {
        //given
        List<HabitDto> allHabits = new ArrayList<>();
        when(habitService.findAllHabits(sessionDto)).thenReturn(allHabits);

        //when
        ResultActions result = mockMvc.perform(
                get("/chebit/list")
                        .sessionAttr("member", sessionDto)
        );

        //then
        result.andExpect(status().isOk())
                .andExpect(view().name("habit/list"))
                .andExpect(model().attributeExists("list"))
                //.andExpect(model().attribute("list", instanceOf(HabitDto.class)))
                .andDo(print());

    }





}