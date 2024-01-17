package com.james.ourht.domain.exercise.movement;

import com.james.ourht.domain.exercise.ExerciseRecord;
import com.james.ourht.domain.exercise.ExerciseRecordRepository;
import com.james.ourht.domain.exercise.ExerciseRecordService;
import com.james.ourht.domain.exercise.ExerciseType;
import com.james.ourht.domain.member.Member;
import com.james.ourht.domain.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MovementServiceTest {

    @Autowired
    ExerciseRecordService exerciseRecordService;

    @Autowired
    MovementService movementService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ExerciseRecordRepository exerciseRecordRepository;

    @Autowired
    MovementRepository movementRepository;

    @Test
    void 스쿼트10번_하기() {
        // given
        Member member = createMember();
        Long exerciseId = exerciseRecordService.startExercise(member, ExerciseType.SQUAT);

        // when
        int squatCount = 10;
        for (int i = 0; i < squatCount; i++) {
            movementService.makeMovement(ExerciseType.SQUAT, exerciseId, MovementAccuracy.PERFECT, true);
        }
        exerciseRecordService.stopExercise(exerciseId);

        // then
        ExerciseRecord record = exerciseRecordRepository.findById(exerciseId).get();
        
            // record 객체의 컬렉션 필드 검사
        List<Movement> movementsFromRecordObject = record.getMovements();
        assertThat(movementsFromRecordObject.size()).isEqualTo(squatCount);
        for (int i = 0; i < squatCount; i++) {
            Movement movement = movementsFromRecordObject.get(i);
            assertThat(movement.getExerciseRecord().getId()).isEqualTo(exerciseId);
        }
            
            // repository에서 찾아온 movement 리스트 검사
        List<Movement> movementsFromRepository = movementRepository.findAll();
        assertThat(movementsFromRepository.size()).isEqualTo(squatCount);
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("김테스트");
        member.setAge(25);
        member.setEmail("test@gmail.com");
        memberRepository.save(member);

        return member;
    }
}