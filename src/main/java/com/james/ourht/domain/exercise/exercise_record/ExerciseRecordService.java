package com.james.ourht.domain.exercise.exercise_record;

import com.james.ourht.domain.exercise.ExerciseType;
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
    private final ExerciseRecordRepositoryForCollection exerciseRecordRepositoryForCollection;

    public Long startExercise(Member member, ExerciseType exerciseType) {
        ExerciseRecord record = new ExerciseRecord();
        record.setMember(member);
        record.setExerciseType(exerciseType);
        record.setStartedAt(LocalDateTime.now());

        exerciseRecordRepository.save(record);
        return record.getId();
    }

    public Long stopExercise(Long recordId) {
        ExerciseRecord record = exerciseRecordRepository.findById(recordId).get();
        record.setEndedAt(LocalDateTime.now());

        return record.getId();
    }

    // todo: 한 유저의 운동 전체 기록 조회
    public List<ExerciseRecord> findRecords(Member member) {
        List<ExerciseRecord> records = exerciseRecordRepositoryForCollection.findExerciseRecordsByMemberUsingFetchJoin(member);

        return records;
    }

    // todo: 운동 타입별 기록 조회

    // todo: 날짜별 조회





}
