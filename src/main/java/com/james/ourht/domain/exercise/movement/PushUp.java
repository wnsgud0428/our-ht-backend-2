package com.james.ourht.domain.exercise.movement;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("PUSH-UP")
@Getter @Setter
public class PushUp extends Movement{
    private boolean isHipGood;
}
