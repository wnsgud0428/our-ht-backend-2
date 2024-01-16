package com.james.ourht.domain.exercise;

import com.james.ourht.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class ExerciseRecord {
    @Id @GeneratedValue
    @Column(name = "record_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private ExerciseType exerciseType;

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

}
