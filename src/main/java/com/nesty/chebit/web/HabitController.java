package com.nesty.chebit.web;

import com.nesty.chebit.service.HabitService;
import com.nesty.chebit.service.MemberService;
import com.nesty.chebit.web.dto.HabitRequestDto;
import com.nesty.chebit.web.dto.MemberLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HabitController {

    private final HabitService habitService;
    private final MemberService memberService;

    @GetMapping("/chebit/main")
    public String mainHabitList(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        MemberLoginDto member = (MemberLoginDto) session.getAttribute("member");
        Long member_id = memberService.findId(member.getEmail());
        List<HabitRequestDto> todayHabitList = habitService.findHabits(member_id);
        model.addAttribute("list", todayHabitList);


        return "index2";
    }
}
