package com.nesty.chebit.web;


import com.nesty.chebit.service.MemberService;
import com.nesty.chebit.web.dto.MemberJoinRequestDto;
import com.nesty.chebit.web.dto.MemberSessionDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Test
    public void 회원가입_테스트_정상가입() throws Exception {
        //given
        when(memberService.join(any())).thenReturn(5L);

        //when
        ResultActions resultActions =
                mockMvc.perform(
                        post("/join")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("name", "name")
                                .param("pwd", "1234")
                                .param("email", "email")
                );

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(model().attribute("joinSuccess", 5L))
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("login"))
                .andExpect(content().string(containsString("회원가입이 완료되었습니다")))
                .andDo(print());
    }

    @Test
    public void 회원가입_테스트_누락항목존재시() throws Exception {
        //given

        //when
        ResultActions resultActions =
                mockMvc.perform(
                        post("/join")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                );

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("joinSuccess"))
                .andExpect(model().hasErrors())
                .andExpect(view().name("joinForm"))
                .andDo(print());

    }

    @Test
    public void 회원가입_테스트_예외발생() throws Exception {
        //given
        when(memberService.join(any())).thenThrow(new IllegalStateException("이미 존재하는 이메일 입니다."));
        String name = "testName";
        String email = "email@mail.com";
        String pwd = "1234";
        MemberJoinRequestDto dto = new MemberJoinRequestDto(name, email, pwd);

        //when
        //예외발생시 컨트롤러에 있는 ExceptionHanler가 처리
        //예외핸들러안에서 기존에 입력한 값을 value 값으로 전달
        //예외의 메세지를 "errorMessage" 전달
        ResultActions resultActions = mockMvc.perform(
                post("/join")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", name)
                        .param("email", email)
                        .param("pwd", pwd));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(model().attribute("value", samePropertyValuesAs(dto)))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(view().name("joinForm"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalStateException))
                .andDo(print());
    }

    @Test
    public void 로그인_테스트_로그인성공() throws Exception {
        //given
        MemberSessionDto sessionDto = new MemberSessionDto(1L, "jenny", "lm@mail.com");
        when(memberService.login(any())).thenReturn(sessionDto);

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", "lm@mail.com")
                        .param("pwd", "1234")
        );

        //then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/chebit/main"))
                .andExpect(result -> assertNotNull(result.getRequest().getSession().getAttribute("member")))
                .andDo(print());
    }

    @Test
    public void 로그인_테스트_로그인실패() throws Exception {
        //given
        MemberSessionDto sessionDto = new MemberSessionDto();
        when(memberService.login(any())).thenReturn(sessionDto);

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .sessionAttr("member", sessionDto));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(request().sessionAttributeDoesNotExist("member"))
                .andExpect(model().attribute("message", "이메일, 또는 비밀번호가 맞지 않습니다."))
                .andDo(print());

    }

    @Test
    public void 로그아웃() throws Exception {
        //given
        MemberSessionDto sessionDto = new MemberSessionDto();

        //when
        ResultActions resultActions = mockMvc.perform(
                get("/logout")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .sessionAttr("member", sessionDto)
        );

        //then

        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(request().sessionAttributeDoesNotExist("member"))
                .andDo(print());

    }


}
