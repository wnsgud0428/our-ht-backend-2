package com.james.ourht.domain.exercise.movement;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("SQUAT")
@Getter @Setter
public class Squat extends Movement{
    private boolean isBackNotBent;
}
