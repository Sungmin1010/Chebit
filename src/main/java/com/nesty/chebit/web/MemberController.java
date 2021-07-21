package com.nesty.chebit.web;

import com.nesty.chebit.service.MemberService;
import com.nesty.chebit.web.dto.MemberJoinRequestDto;
import com.nesty.chebit.web.dto.MemberLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@SessionAttributes("member")
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
            session.removeAttribute("member");
        }
        List<MemberLoginDto> findMember = memberService.findMember(memberLoginDto);
        if(!findMember.isEmpty() && findMember.get(0).getEmail().equals(memberLoginDto.getEmail())){
            session.setAttribute("member", findMember.get(0));
           return "redirect:/chebit/main";
        }else{
            return "redirect:/";
        }
    }
}
