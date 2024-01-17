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
            Squat squat = new Squat();
            squat.setBackNotBent(criticalFlag);
            movement = squat;
        } else if (exerciseType == ExerciseType.PUSH_UP) {
            PushUp pushUp = new PushUp();
            pushUp.setHipGood(criticalFlag);
            movement = pushUp;
        } else if (exerciseType == ExerciseType.PULL_UP) {
            PullUp pullUp = new PullUp();
            pullUp.setScapulaMoveGood(criticalFlag);
            movement = pullUp;
        }
        movement.setExerciseRecord(record);
        movement.setAccuracy(accuracy);
        movement.setMovementTime(LocalDateTime.now());

        movementRepository.save(movement);

        return movement.getId();
    }
}
