package com.james.ourht.domain.exercise.movement;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("PULL-UP")
@Getter @Setter
public class PullUp extends Movement{
    private boolean isScapulaMoveGood;
}
