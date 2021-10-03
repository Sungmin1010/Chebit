package com.nesty.chebit.web;

import com.nesty.chebit.config.LoginMember;
import com.nesty.chebit.service.MemberService;
import com.nesty.chebit.web.dto.MemberJoinRequestDto;
import com.nesty.chebit.web.dto.MemberLoginDto;
import com.nesty.chebit.web.dto.MemberSessionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public String join(@Valid MemberJoinRequestDto memberJoinRequestDto,BindingResult bindingResult, Model model){
        log.info("----POST /join [회원가입]-----");
        if(bindingResult.hasErrors()){
            return "joinForm";
        }
        Long id = memberService.join(memberJoinRequestDto);
        model.addAttribute("joinSuccess", id);
        return "login";

    }

    @ExceptionHandler(IllegalStateException.class)
    public ModelAndView memberError(IllegalStateException e, HttpServletRequest request){
        log.info("----예외 핸들러 -----");
        MemberJoinRequestDto dto = new MemberJoinRequestDto(request.getParameter("name"), request.getParameter("email"), request.getParameter("pwd"));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("joinForm");
        modelAndView.addObject("value", dto);
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }



    @PostMapping("/login")
    public String login(MemberLoginDto memberLoginDto, HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        if(!ObjectUtils.isEmpty(session.getAttribute("member"))){
            session.removeAttribute("member");
        }

        MemberSessionDto findDto = memberService.login(memberLoginDto);
        if(!findDto.isEmpty()){
            session.setAttribute("member", findDto);
            return "redirect:/chebit/main";
        }else{
            String message = "이메일, 또는 비밀번호가 맞지 않습니다.";
            model.addAttribute("message", message);
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        log.info("----로그아웃 -----");
        HttpSession session = request.getSession();
        session.removeAttribute("member");
        return "redirect:/";
    }
}
