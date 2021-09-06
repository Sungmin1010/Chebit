package com.nesty.chebit.web;

import com.nesty.chebit.config.LoginMember;
import com.nesty.chebit.service.HabitService;
import com.nesty.chebit.service.MemberService;
import com.nesty.chebit.web.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
public class HabitController {

    private final HabitService habitService;
    //private final MemberService memberService;

    @GetMapping("/chebit/main")
    public String mainHabitList(Model model, @LoginMember MemberSessionDto member){
        log.info("----GET /chebit/main [메인 화면] ---");
        Long member_id = member.getId();
        List<HabitRequestDto> todayHabitList = habitService.findHabits(member_id);
        model.addAttribute("list", todayHabitList);

        return "index2";
    }

    @GetMapping("/chebit/list")
    public String habitList(@LoginMember MemberSessionDto member, Model model){
        log.info("----GET /chebit/list [습관 리스트] ----");
        List<HabitDto> allHabits = habitService.findAllHabits(member);
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
    public String addHabit(@Valid HabitFormDto habitFormDto, BindingResult result, Model model, @LoginMember MemberSessionDto member ){
        log.info("----POST /chebit/list/add [새로운 습관 등록]-----");
        if(result.hasErrors()){
            log.info("errors");
            model.addAttribute("errorForm", habitFormDto);
            List<FieldError> fieldErrors = result.getFieldErrors();

            for(FieldError e:fieldErrors){
                model.addAttribute(e.getField()+"Error", true);
            }
            return "/habit/habitform";
        }
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
        habitService.updateHabit(habitDto);
        return "redirect:/chebit/list";
    }

    @DeleteMapping("/chebit/list/delete/{id}")
    @ResponseBody
    public Integer deleteHabit(@PathVariable Long id){
        log.info("----DELETE /chebit/list/delete [습관 삭제]-----");
        return habitService.deleteHabitWithAllRecord(id);
    }

    @GetMapping("/chebit/weekly")
    public String getWeekly(@LoginMember MemberSessionDto member, Model model){
        log.info("----GET /chebit/monthly [위클리 화면]-----");
        LocalDate today = LocalDate.now();
        List<WeeklyHabitDto> habitWithWeeklyRecord = habitService.findHabitWithWeeklyRecord(member.getId(), today);
        List<WeeklyDateDto> date = habitService.getWeeklyDate(today);

        model.addAttribute("list", habitWithWeeklyRecord);
        model.addAttribute("head", date);
        model.addAttribute("today", today);

        return "weekly/weeklyHabit";
    }
}
