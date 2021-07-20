package com.nesty.chebit.web;

import com.nesty.chebit.domain.Member;
import com.nesty.chebit.service.MemberService;
import com.nesty.chebit.web.dto.MemberJoinRequestDto;
import com.nesty.chebit.web.dto.MemberLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String login(MemberLoginDto memberLoginDto, RedirectAttributes redirectAttributes){
        List<MemberLoginDto> findMember = memberService.findMember(memberLoginDto);
        if(!findMember.isEmpty() && findMember.get(0).getEmail().equals(memberLoginDto.getEmail())){
            redirectAttributes.addFlashAttribute("member", findMember.get(0));
           return "redirect:/main";
        }else{
            return "redirect:/";
        }
    }
}
