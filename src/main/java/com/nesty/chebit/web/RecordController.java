package com.nesty.chebit.web;

import com.nesty.chebit.service.RecordService;
import com.nesty.chebit.web.dto.RecordAddDto;
import com.nesty.chebit.web.dto.WeeklyRemoveRecordDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @PostMapping("/chebit/record")
    @ResponseBody
    public ResponseEntity<RecordAddDto> saveTodayRecord(@RequestBody RecordAddDto recordAddDto, Model model){
        log.info("----POST /chebit/record [기록 추가]-----");
        recordAddDto.convertToLocalDate();
        Long recordId = recordService.addTodayRecord(recordAddDto.getHabitId(), recordAddDto.getRecDate());
        recordAddDto.setRecordId(recordId);
        return ResponseEntity.ok(recordAddDto);
    }

    @DeleteMapping("/chebit/weekly/record")
    @ResponseBody
    public int removeRecord(@RequestBody WeeklyRemoveRecordDto weeklyRemoveRecordDto){
        log.info("----DELETE /chebit/record [기록 삭제]-----");
        log.info(weeklyRemoveRecordDto.toString());
        return recordService.removeRecord(weeklyRemoveRecordDto);
    }

    @PostMapping("/chebit/weekly/record")
    @ResponseBody
    public Long saveRecord(@RequestBody WeeklyRemoveRecordDto weeklyRemoveRecordDto){
        log.info("----POST /chebit/record [기록 추가]-----");
        return recordService.addTodayRecord(weeklyRemoveRecordDto.getHabitId(), weeklyRemoveRecordDto.getRecDateToLocalDate());

    }

    @ExceptionHandler(Exception.class)
    public ModelAndView commonError(HttpServletRequest req, Exception e){
        ModelAndView mv = new ModelAndView();
        mv.addObject("message", e.getMessage());
        return mv;
    }



}
