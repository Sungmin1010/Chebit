package com.nesty.chebit.service;

import com.nesty.chebit.domain.Habit;
import com.nesty.chebit.domain.Member;
import com.nesty.chebit.domain.Record;
import com.nesty.chebit.repository.HabitRepository;
import com.nesty.chebit.repository.MemberRepository;
import com.nesty.chebit.repository.RecordRepository;
import com.nesty.chebit.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HabitService {

    private final HabitRepository habitRepository;
    private final MemberRepository memberRepository;
    private final RecordRepository recordRepository;

    /**
     * 특정 회원의 습관 리스트 조회
    */
    public List<HabitRequestDto> findHabits(Long memberId){
        Member member = memberRepository.findOneMember(memberId);
        LocalDate today = LocalDate.now();
        List<HabitRequestDto> habitRequestDtoList = new ArrayList<>();

        List<Habit> findHabits = habitRepository.findByMemberWithStartDate(member,today);
        for(Habit h : findHabits){
            //오늘 기록이 있는지 확인 & Entity -> DTO 변환
            int a = recordRepository.findTodayRecord(h, today).size();
            HabitRequestDto dto = new HabitRequestDto(h, a);
            habitRequestDtoList.add(dto);
        }
        return habitRequestDtoList;
    }

    /**
     * 새로운 습관 추가 서비스
     */
    @Transactional
    public Long addHabit(HabitFormDto habitFormDto, MemberSessionDto memberSessionDto){
        //멤버조회
        Member member = memberRepository.findOneMember(memberSessionDto.getId());

        //습관엔티티
        Habit habit = Habit.createHabit(habitFormDto.getTitle(), habitFormDto.getMemo(), LocalDate.parse(habitFormDto.getSdate(), DateTimeFormatter.ISO_DATE), member);

        return habitRepository.saveHabit(habit);


    }

    /**
     * 습관 리스트 조회
     *
     */
    public List<HabitDto> findAllHabits(MemberSessionDto memberSessionDto){
        //멤버조회
        Member member = memberRepository.findOneMember(memberSessionDto.getId());

        return member.getHabits().stream()
                .map( o -> new HabitDto(o))
                .collect(Collectors.toList());

    }

    /**
     * 습관 수정
     *
     */
    @Transactional
    public Long updateHabit(HabitDto habitDto){
        //습관 엔티티
        Habit habit = habitRepository.findOneHabit(habitDto.getId());

        return habit.edit(habitDto.getTitle(), habitDto.getMemo());

    }

    /**
     * 습관 수정하기 위해 id로 엔티티 정보 가져오기
     */
    public HabitDto findById(Long id){
        Habit habit = habitRepository.findOneHabit(id);
        return new HabitDto(habit);
    }

    /**
     * deleteHabit 습관 삭제 서비스
     * - 습관 삭제시 모든 기록도 함께 삭제
     */
    @Transactional
    public int deleteHabitWithAllRecord(Long habitId){
        //습관 아이디로 엔티티 조회
        //습관에 기록이 1개 이상이면 기록부터 지우기.
        //기록 지우고 습관 지우기
        Habit habit = habitRepository.findOneHabit(habitId);
        int recordResult = 0;
        if(habit.getRecords().size() > 0){
            recordResult = recordRepository.removeAllRecords(habit);
        }
        habitRepository.remove(habit);

        return recordResult;
    }

    /**
     * 위클리 습관 조회 서비스
     */
    public List<WeeklyHabitDto> findHabitWithWeeklyRecord(Long memberId, LocalDate today){
        Member member = memberRepository.findOneMember(memberId);

        LocalDate sdate = getWeeklyStartDate(today);
        LocalDate edate = getWeeklyEndDate(today);

        List<Habit> habits = member.getHabits();
        List<WeeklyHabitDto> list = new ArrayList<>();
        //방법1. habit의 리스트들 찾아서 habit 마다 특정 기간 기록 조회 //방법2. join 걸어서 한꺼번에 가져오기
        if(!habits.isEmpty()){
            for(Habit h:habits){
                WeeklyHabitDto weeklyHabitDto = new WeeklyHabitDto(h);
                //weeklyHabitDto.setWeeklyRecordDto(sdate); //위클리 기록 초기화
                List<WeeklyRecordDto> collect = recordRepository.findWeeklyRecords(h, sdate, edate).stream().map(
                        o -> new WeeklyRecordDto(o)
                ).collect(Collectors.toList());
                weeklyHabitDto.setWeeklyRecordDto(sdate, collect); //위클리 기록 초기화
                list.add(weeklyHabitDto);
            }
        }

        return list;


    }

    private LocalDate getWeeklyStartDate(LocalDate today){
        int dayOfWeek = today.getDayOfWeek().getValue(); //Mon : 1 ~ Sun : 7
        return today.minusDays(dayOfWeek-1);
    }
    public LocalDate getWeeklyEndDate(LocalDate today){
        int dayOfWeek = today.getDayOfWeek().getValue(); //Mon : 1 ~ Sun : 7
        return today.plusDays(7-dayOfWeek);
    }


}
