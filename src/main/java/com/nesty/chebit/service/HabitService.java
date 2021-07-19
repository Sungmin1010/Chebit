package com.nesty.chebit.service;

import com.nesty.chebit.domain.Habit;
import com.nesty.chebit.domain.Member;
import com.nesty.chebit.repository.HabitRepository;
import com.nesty.chebit.repository.MemberRepository;
import com.nesty.chebit.repository.RecordRepository;
import com.nesty.chebit.web.dto.HabitRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

}
