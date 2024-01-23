package com.james.ourht.domain.exercise.movement;

import com.james.ourht.domain.exercise.exercise_record.ExerciseRecord;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("PULL-UP")
@Getter @Setter
public class PullUp extends Movement{
    private boolean isScapulaMoveGood;

    @Override
    public Movement createMovement(ExerciseRecord record, MovementAccuracy accuracy, boolean criticalFlag) {
        PullUp pullUp = new PullUp();
        pullUp.setExerciseRecord(record);
        pullUp.setAccuracy(accuracy);
        pullUp.setMovementTime(LocalDateTime.now());
        pullUp.setScapulaMoveGood(criticalFlag);

        return pullUp;
    }
}
