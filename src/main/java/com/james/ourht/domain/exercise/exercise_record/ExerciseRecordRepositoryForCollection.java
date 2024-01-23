package com.james.ourht.domain.exercise.exercise_record;

import com.james.ourht.domain.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ExerciseRecordRepositoryForCollection {
    private final EntityManager em;

    public List<ExerciseRecord> findExerciseRecordsByMember(Member member) {
        String jpql = "select er from ExerciseRecord er" +
                " join er.movements" +
                " where er.member = :member";

        TypedQuery<ExerciseRecord> query = em.createQuery(jpql, ExerciseRecord.class);
        query.setParameter("member", member);

        return query.getResultList();
    }

    public List<ExerciseRecord> findExerciseRecordsByMemberUsingFetchJoin(Member member) {
        String jpql = "select er from ExerciseRecord er" +
                " join fetch er.movements" +
                " where er.member = :member";

        TypedQuery<ExerciseRecord> query = em.createQuery(jpql, ExerciseRecord.class);
        query.setParameter("member", member);

        return query.getResultList();
    }
}
