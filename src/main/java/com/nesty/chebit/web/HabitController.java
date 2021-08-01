package com.nesty.chebit.web;

import com.nesty.chebit.service.HabitService;
import com.nesty.chebit.service.MemberService;
import com.nesty.chebit.web.dto.HabitDto;
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
    public String habitList(@SessionAttribute("member") MemberSessionDto memberSessionDto, Model model){
        log.info("----GET /chebit/list [습관 리스트] ----");

        List<HabitDto> allHabits = habitService.findAllHabits(memberSessionDto);
        model.addAttribute("list", allHabits);
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

    @GetMapping("/chebit/list/update/{id}")
    public String updateHabitForm(@PathVariable Long id, Model model){
        log.info("----GET /chebit/list/update/{id} [습관 수정 양식]-----");
        HabitDto habit = habitService.findById(id);
        model.addAttribute("habit", habit);
        return "habit/habitEditForm";
    }

    @PostMapping("/chebit/list/update/{id}")
    public String updateHabit(@ModelAttribute("habitDto") HabitDto habitDto){
        log.info("----POST /chebit/list/add [습관 수정]-----");
        //log.info("dto id : " + habitDto.getId());
        habitService.updateHabit(habitDto);
        return "redirect:/chebit/list";

    }
}
