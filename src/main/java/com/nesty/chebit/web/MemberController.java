package com.nesty.chebit.web;

import com.nesty.chebit.service.MemberService;
import com.nesty.chebit.web.dto.MemberJoinRequestDto;
import com.nesty.chebit.web.dto.MemberLoginDto;
import com.nesty.chebit.web.dto.MemberSessionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public String join(MemberJoinRequestDto memberJoinRequestDto){

        Long id = memberService.join(memberJoinRequestDto);

        return "redirect:/";

    }

    @PostMapping("/login")
    public String login(MemberLoginDto memberLoginDto, HttpServletRequest request){
        HttpSession session = request.getSession();
        if(!ObjectUtils.isEmpty(session.getAttribute("member"))){
            System.out.println("세션삭제");
            session.removeAttribute("member");
        }

        MemberSessionDto findDto = memberService.login(memberLoginDto);
        if(!findDto.getEmail().isEmpty()){
            session.setAttribute("member", findDto);
            return "redirect:/chebit/main";
        }else{
            return "redirect:/";
        }
    }
}
