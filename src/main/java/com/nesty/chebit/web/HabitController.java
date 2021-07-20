package com.nesty.chebit.web;

import com.nesty.chebit.domain.Member;
import com.nesty.chebit.repository.HabitRepository;
import com.nesty.chebit.service.HabitService;
import com.nesty.chebit.service.MemberService;
import com.nesty.chebit.web.dto.HabitRequestDto;
import com.nesty.chebit.web.dto.MemberLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HabitController {

    private final HabitService habitService;
    private final MemberService memberService;

    @GetMapping("/main")
    public String mainHabitList(@ModelAttribute MemberLoginDto memberLoginDto, Model model){
        Long member_id = memberService.findId(memberLoginDto.getEmail());
        List<HabitRequestDto> todayHabitList = habitService.findHabits(member_id);
        model.addAttribute("list", todayHabitList);


        return "index2";
    }
}
