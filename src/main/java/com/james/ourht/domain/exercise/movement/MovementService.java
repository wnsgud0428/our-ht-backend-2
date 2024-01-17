package com.james.ourht.domain.exercise.movement;

import com.james.ourht.domain.exercise.ExerciseRecord;
import com.james.ourht.domain.exercise.ExerciseRecordRepository;
import com.james.ourht.domain.exercise.ExerciseType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class MovementService {
    private final ExerciseRecordRepository exerciseRecordRepository;
    private final MovementRepository movementRepository;

    public Long makeMovement(ExerciseType exerciseType, Long exerciseId, MovementAccuracy accuracy, boolean criticalFlag) {
        ExerciseRecord record = exerciseRecordRepository.findById(exerciseId).get();

        Movement movement = null;
        // 안좋은 설계...
        if (exerciseType == ExerciseType.SQUAT) {
            movement = new Squat().createMovement(record, accuracy, criticalFlag);
        } else if (exerciseType == ExerciseType.PUSH_UP) {
            movement = new PushUp().createMovement(record, accuracy, criticalFlag);
        } else if (exerciseType == ExerciseType.PULL_UP) {
            movement = new PullUp().createMovement(record, accuracy, criticalFlag);
        }

        movementRepository.save(movement);

        return movement.getId();
    }
}
