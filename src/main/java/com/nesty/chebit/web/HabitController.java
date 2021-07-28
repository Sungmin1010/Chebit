package com.nesty.chebit.web;

import com.nesty.chebit.service.HabitService;
import com.nesty.chebit.service.MemberService;
import com.nesty.chebit.web.dto.HabitFormDto;
import com.nesty.chebit.web.dto.HabitRequestDto;
import com.nesty.chebit.web.dto.MemberSessionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
public class HabitController {

    private final HabitService habitService;
    private final MemberService memberService;

    @GetMapping("/chebit/main")
    public String mainHabitList(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        MemberSessionDto member = (MemberSessionDto) session.getAttribute("member");
        Long member_id = memberService.findId(member.getEmail());
        List<HabitRequestDto> todayHabitList = habitService.findHabits(member_id);
        model.addAttribute("list", todayHabitList);


        return "index2";
    }

    @GetMapping("/chebit/list")
    public String habitList(){

        return "habit/list";
    }

    @GetMapping("/chebit/list/add")
    public String addHabit(Model model){
        log.info("----GET /chebit/list/add [습관 등록 양식]-----");
        model.addAttribute("habitFormDto", new HabitFormDto());


        return "habit/habitform";
    }

    @PostMapping("/chebit/list/add")
    public String addHabit(@Valid HabitFormDto habitFormDto, BindingResult result, Model model, HttpServletRequest request ){
        log.info("----POST /chebit/list/add [새로운 습관 등록]-----");
        HttpSession session = request.getSession();
        if(result.hasErrors()){
            log.info("errors");
            model.addAttribute("habitFormDto", habitFormDto);
            List<FieldError> fieldErrors = result.getFieldErrors();
            for(FieldError e:fieldErrors){
                model.addAttribute(e.getField(), true);
            }
            return "/habit/habitform";
        }

        MemberSessionDto member = (MemberSessionDto)session.getAttribute("member");
        Long id = habitService.addHabit(habitFormDto, member);
        return "redirect:/chebit/list";
    }
}
