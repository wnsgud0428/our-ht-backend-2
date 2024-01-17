package com.james.ourht.domain.exercise;

import com.james.ourht.domain.member.Member;
import com.james.ourht.domain.member.MemberRepository;
import com.james.ourht.domain.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
//@Transactional
class ExerciseRecordServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ExerciseRecordService exerciseRecordService;
    @Autowired
    ExerciseRecordRepository exerciseRecordRepository;


    @Test
    void 운동시작하고_멈추기() {
        // given
        Member member = createMember();

        // when
        Long recordId = exerciseRecordService.startExercise(member, ExerciseType.SQUAT);
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        exerciseRecordService.stopExercise(recordId);

        // then
        ExerciseRecord record = exerciseRecordRepository.findById(recordId).get();
        Assertions.assertThat(record.getMember().getId()).isEqualTo(member.getId());
        Assertions.assertThat(record.getEndedAt()).isNotNull();
    }
    
    @Test
    void 운동기록_전체조회() {
        // given
        Member member = createMember();

        int exerciseCount = 10;
        for (int i = 0; i < exerciseCount; i++) {
            Long recordId = exerciseRecordService.startExercise(member, ExerciseType.SQUAT);
            exerciseRecordService.stopExercise(recordId);
        }

        // when
        List<ExerciseRecord> records = exerciseRecordService.findRecords();

        // then
        Assertions.assertThat(records.size()).isEqualTo(exerciseCount);

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