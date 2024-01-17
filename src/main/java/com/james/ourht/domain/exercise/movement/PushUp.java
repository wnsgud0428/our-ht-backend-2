package com.james.ourht.domain.exercise.movement;

import com.james.ourht.domain.exercise.ExerciseRecord;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("PUSH")
@Getter @Setter
public class PushUp extends Movement{
    private boolean isHipGood;

    @Override
    public Movement createMovement(ExerciseRecord record, MovementAccuracy accuracy, boolean criticalFlag) {
        PushUp pushUp = new PushUp();
        pushUp.setExerciseRecord(record);
        pushUp.setAccuracy(accuracy);
        pushUp.setMovementTime(LocalDateTime.now());
        pushUp.setHipGood(criticalFlag);

        return pushUp;
    }
}
