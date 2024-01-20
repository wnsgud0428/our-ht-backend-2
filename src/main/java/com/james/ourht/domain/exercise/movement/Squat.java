package com.james.ourht.domain.exercise.movement;

import com.james.ourht.domain.exercise.ExerciseRecord;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("SQUAT")
@Getter @Setter
public class Squat extends Movement{
    private boolean isBackNotBent;

    @Override
    public Movement createMovement(ExerciseRecord record, MovementAccuracy accuracy, boolean criticalFlag) {
        Squat squat = new Squat();
        squat.setExerciseRecord(record);
        squat.setAccuracy(accuracy);
        squat.setMovementTime(LocalDateTime.now());
        squat.setBackNotBent(criticalFlag);

        return squat;
    }
}
