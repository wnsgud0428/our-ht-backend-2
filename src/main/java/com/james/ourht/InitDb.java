package com.james.ourht;

import com.james.ourht.domain.exercise.ExerciseType;
import com.james.ourht.domain.exercise.exercise_record.ExerciseRecordService;
import com.james.ourht.domain.exercise.movement.MovementAccuracy;
import com.james.ourht.domain.exercise.movement.MovementService;
import com.james.ourht.domain.member.Member;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        private final ExerciseRecordService exerciseRecordService;
        private final MovementService movementService;

        public void dbInit1() {
            Member member = createMember("김철수", "cs@cs.com", 25);
            em.persist(member);

            doExercise(member, ExerciseType.SQUAT, 2);
            doExercise(member, ExerciseType.PULL_UP, 3);
            doExercise(member, ExerciseType.SQUAT, 4);
            doExercise(member, ExerciseType.SQUAT, 5);

        }

        public void dbInit2() {
            Member member = createMember("박지성", "js@js.com", 25);
            em.persist(member);

            doExercise(member, ExerciseType.SQUAT, 2);
            doExercise(member, ExerciseType.SQUAT, 3);
        }

        private Member createMember(String name, String email, int age) {
            Member member = new Member();
            member.setName(name);
            member.setEmail(email);
            member.setAge(age);

            return member;
        }

        private void doExercise(Member member, ExerciseType exerciseType, int exerciseCount) {
            Long exerciseRecordId = exerciseRecordService.startExercise(member, exerciseType);
            for (int i = 0; i < exerciseCount; i++) {
                movementService.makeMovement(exerciseType, exerciseRecordId, MovementAccuracy.PERFECT, true);
            }
            exerciseRecordService.stopExercise(exerciseRecordId);
        }



    }


}
