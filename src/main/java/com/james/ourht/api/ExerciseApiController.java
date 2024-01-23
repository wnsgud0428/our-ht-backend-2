package com.james.ourht.api;

import com.james.ourht.domain.exercise.exercise_record.ExerciseRecord;
import com.james.ourht.domain.exercise.exercise_record.ExerciseRecordRepository;
import com.james.ourht.domain.exercise.exercise_record.ExerciseRecordService;
import com.james.ourht.domain.exercise.ExerciseType;
import com.james.ourht.domain.exercise.movement.Movement;
import com.james.ourht.domain.exercise.movement.MovementAccuracy;
import com.james.ourht.domain.exercise.movement.MovementService;
import com.james.ourht.domain.member.Member;
import com.james.ourht.domain.member.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/api/member/{memberId}/exercises")
    public List<ExerciseRecordDto> getExerciseRecordByMemberId(@PathVariable("memberId") Long memberId) {
        Member member = memberService.findMemberById(memberId);

        List<ExerciseRecord> records = exerciseRecordService.findRecords(member);
        List<ExerciseRecordDto> results = records.stream()
                .map(o -> new ExerciseRecordDto(o))
                .collect(Collectors.toList());

        return results;
    }

    @Data
    static class ExerciseRecordDto {
        private Long exerciseRecordId;
        private ExerciseType exerciseType;
        private Member member;
        private LocalDateTime startedAt;
        private LocalDateTime endedAt;
        private List<MovementDto> movements;

        public ExerciseRecordDto(ExerciseRecord exerciseRecord) {
            exerciseRecordId = exerciseRecord.getId();
            exerciseType = exerciseRecord.getExerciseType();
            member = exerciseRecord.getMember();
            startedAt = exerciseRecord.getStartedAt();
            endedAt = exerciseRecord.getEndedAt();
            movements = exerciseRecord.getMovements().stream()
                    .map(o -> new MovementDto(o))
                    .collect(Collectors.toList());
        }
    }

    @Data
    static class MovementDto {
        private Long movementId;
        private MovementAccuracy movementAccuracy;
        private LocalDateTime movementTime;

        public MovementDto(Movement movement) {
            movementId = movement.getId();
            movementAccuracy = movement.getAccuracy();
            movementTime = movement.getMovementTime();
        }
    }

}
