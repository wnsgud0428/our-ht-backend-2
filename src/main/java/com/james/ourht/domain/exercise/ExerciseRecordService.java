package com.james.ourht.domain.exercise;

import com.james.ourht.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ExerciseRecordService {
    private final ExerciseRecordRepository exerciseRecordRepository;

    public Long startExercise(Member member, String exerciseType) {
        ExerciseRecord record = new ExerciseRecord();
        record.setMember(member);
        record.setExerciseType(ExerciseType.valueOf(exerciseType));
        record.setStartedAt(LocalDateTime.now());

        exerciseRecordRepository.save(record);
        return record.getId();
    }

    public Long stopExercise(Long recordId) {
        ExerciseRecord record = exerciseRecordRepository.findById(recordId).get();
        record.setEndedAt(LocalDateTime.now());

        return record.getId();
    }

    // todo: 유저의 전체 기록 조회
    public List<ExerciseRecord> findRecords() {
        List<ExerciseRecord> records = exerciseRecordRepository.findAll();

        return records;
    }

    // todo: 운동 타입별 기록 조회

    // todo: 날짜별 조회





}
