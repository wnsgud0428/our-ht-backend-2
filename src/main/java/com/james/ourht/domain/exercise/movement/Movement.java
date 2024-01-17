package com.james.ourht.domain.exercise.movement;

import com.james.ourht.domain.BaseTimeEntity;
import com.james.ourht.domain.exercise.ExerciseRecord;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@DiscriminatorColumn(name = "exercise_type")
@Getter @Setter
public abstract class Movement {

    @Id @GeneratedValue
    @Column(name = "movement_id")
    private Long id;

    @ManyToOne
//    @JoinColumn(name = "record_id")
    private ExerciseRecord exerciseRecord;

    @Enumerated(EnumType.STRING)
    private MovementAccuracy accuracy;

    private LocalDateTime movementTime;

}
