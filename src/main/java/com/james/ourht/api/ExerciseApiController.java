package com.james.ourht.api;

import com.james.ourht.domain.exercise.ExerciseRecord;
import com.james.ourht.domain.exercise.ExerciseRecordRepository;
import com.james.ourht.domain.exercise.ExerciseRecordService;
import com.james.ourht.domain.exercise.ExerciseType;
import com.james.ourht.domain.exercise.movement.MovementAccuracy;
import com.james.ourht.domain.exercise.movement.MovementService;
import com.james.ourht.domain.member.Member;
import com.james.ourht.domain.member.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class ExerciseApiController {
    private final MemberService memberService;
    private final ExerciseRecordService exerciseRecordService;
    private final ExerciseRecordRepository exerciseRecordRepository;

    private final MovementService movementService;


    @PostMapping("/api/exercises/start")
    public StartExerciseResponse startExercises(@RequestBody StartExerciseRequest request) {
        Member member = memberService.findMemberById(request.getMemberId());
        String exerciseTypeString = request.getExerciseType();
        Long recordId = exerciseRecordService.startExercise(member, ExerciseType.valueOf(exerciseTypeString));

        ExerciseRecord record = exerciseRecordRepository.findById(recordId).get();

        return new StartExerciseResponse(recordId, record.getStartedAt());
    }

    @Data
    static class StartExerciseRequest {
        private Long memberId;
        private String exerciseType;
    }

    @Data
    static class StartExerciseResponse {
        private Long recordId;
        private LocalDateTime startedAt;

        public StartExerciseResponse(Long recordId, LocalDateTime startedAt) {
            this.recordId = recordId;
            this.startedAt = startedAt;
        }
    }

    @PostMapping("/api/exercises/stop")
    public StopExerciseResponse stopExercise(@RequestBody StopExerciseRequest request) {
        Long recordId = exerciseRecordService.stopExercise(request.getRecordId());

        ExerciseRecord record = exerciseRecordRepository.findById(recordId).get();

        return new StopExerciseResponse(recordId, record.getEndedAt());
    }

    @Data
    static class StopExerciseRequest {
        private Long recordId;
    }

    @Data
    static class StopExerciseResponse {
        private Long recordId;
        private LocalDateTime endedAt;

        public StopExerciseResponse(Long recordId, LocalDateTime endedAt) {
            this.recordId = recordId;
            this.endedAt = endedAt;
        }
    }

    @PostMapping("/api/exercises/{recordId}/movements")
    public CreateMovementResponse makeMovement(@PathVariable("recordId") Long recordId, @RequestBody CreateMovementRequest request) {
        ExerciseRecord record = exerciseRecordRepository.findById(recordId).get();
        Long movementId = movementService.makeMovement(record.getExerciseType(), recordId, MovementAccuracy.valueOf(request.getAccuracy()), request.isCriticalFlag());

        return new CreateMovementResponse(recordId, movementId);
    }

    @Data
    static class CreateMovementRequest {
        private String accuracy;
        private boolean criticalFlag;
    }

    @Data
    static class CreateMovementResponse {
        private Long recordId;
        private Long movementId;

        public CreateMovementResponse(Long recordId, Long movementId) {
            this.recordId = recordId;
            this.movementId = movementId;
        }
    }
}
