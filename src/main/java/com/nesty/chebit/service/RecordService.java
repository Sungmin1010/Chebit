package com.nesty.chebit.service;

import com.nesty.chebit.domain.Habit;
import com.nesty.chebit.domain.Record;
import com.nesty.chebit.repository.HabitRepository;
import com.nesty.chebit.repository.RecordRepository;
import com.nesty.chebit.web.dto.WeeklyRemoveRecordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordService {
    private final RecordRepository recordRepository;
    private final HabitRepository habitRepository;

    /**
     * 특정 습관의 오늘 기록 추가
     */
    @Transactional
    public Long addTodayRecord(Long habitId, LocalDate today){
        Habit habit = habitRepository.findOneHabit(habitId);
        validateDuplicateRecord(habit, today);
        Record record = Record.createNewRecord(habit, today);
        return recordRepository.save(record);
    }

    private void validateDuplicateRecord(Habit habit, LocalDate today){
        List<Record> findRecords = recordRepository.findTodayRecord(habit, today);
        if(!findRecords.isEmpty()){
            throw new IllegalStateException(
                    "["+habit.getTitle()+"] 습관에 대한 오늘("+today.toString()+") 기록이 이미 존재 합니다."
            );
        }
    }
    /**
     * 특정 날짜 기록 삭제
     */
    @Transactional
    public int removeRecord(WeeklyRemoveRecordDto removeRecordDto){
        LocalDate recDate = removeRecordDto.getRecDateToLocalDate();
        return recordRepository.removeRecord(removeRecordDto.getHabitId(), recDate);
    }
}
