package com.epam.upskill.springcore.service.db.specifications;

import com.epam.upskill.springcore.model.Training;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: TrainingSpecifications class for Training entity.
 * This class is used to build dynamic queries for filtering Training entities
 * based on various attributes like trainee ID, trainer ID, training name, etc.
 * @date: 09 November 2023 $
 * @time: 4:18 PM 14 $
 * @author: Qudratjon Komilov
 */
@Builder
@Slf4j
public class TrainingSpecifications implements Specification<Training> {

    private final Long traineeId;
    private final Long trainerId;
    private final String trainingName;
    private final Long trainingTypeId;
    private final Date trainingDate;
    private final Integer trainingDuration;

    public TrainingSpecifications(Long traineeId, Long trainerId, String trainingName, Long trainingTypeId, Date trainingDate, Integer trainingDuration) {
        this.traineeId = traineeId;
        this.trainerId = trainerId;
        this.trainingName = trainingName;
        this.trainingTypeId = trainingTypeId;
        this.trainingDate = trainingDate;
        this.trainingDuration = trainingDuration;
    }

    /**
     * Creates a predicate for filtering Training entities based on specified criteria.
     *
     * @param root            the root type in the from clause
     * @param query           the CriteriaQuery being constructed
     * @param criteriaBuilder used to construct the CriteriaQuery
     * @return a Predicate applying the specified criteria
     */
    @Override
    public Predicate toPredicate(Root<Training> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        addTraineeIdPredicate(predicates, root, criteriaBuilder);
        addTrainerIdPredicate(predicates, root, criteriaBuilder);
        addTrainingNamePredicate(predicates, root, criteriaBuilder);
        addTrainingTypeIdPredicate(predicates, root, criteriaBuilder);
        addTrainingDateAndDurationPredicate(predicates, root, criteriaBuilder);

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    /**
     * Adds a predicate for filtering by trainee ID if the given trainee ID is not null.
     *
     * @param predicates The list of predicates.
     * @param root       The root entity.
     * @param cb         The criteria builder.
     */
    private void addTraineeIdPredicate(List<Predicate> predicates, Root<Training> root, CriteriaBuilder cb) {
        if (traineeId != null) {
            predicates.add(cb.equal(root.get("trainee").get("id"), traineeId));
            log.info("Added trainee ID predicate for {}", traineeId);
        }
    }

    /**
     * Adds a predicate for filtering by trainer ID if the given trainer ID is not null.
     *
     * @param predicates The list of predicates.
     * @param root       The root entity.
     * @param cb         The criteria builder.
     */
    private void addTrainerIdPredicate(List<Predicate> predicates, Root<Training> root, CriteriaBuilder cb) {
        if (trainerId != null) {
            predicates.add(cb.equal(root.get("trainer").get("id"), trainerId));
            log.info("Added trainer ID predicate for {}", trainerId);
        }
    }

    /**
     * Adds a predicate for filtering by training name if the given training name is not null.
     *
     * @param predicates The list of predicates.
     * @param root       The root entity.
     * @param cb         The criteria builder.
     */
    private void addTrainingNamePredicate(List<Predicate> predicates, Root<Training> root, CriteriaBuilder cb) {
        if (trainingName != null && !trainingName.trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + trainingName.toLowerCase() + "%"));
            log.info("Added training name predicate for {}", trainingName);
        }
    }

    /**
     * Adds a predicate for filtering by training type ID if the given training type ID is not null.
     *
     * @param predicates The list of predicates.
     * @param root       The root entity.
     * @param cb         The criteria builder.
     */
    private void addTrainingTypeIdPredicate(List<Predicate> predicates, Root<Training> root, CriteriaBuilder cb) {
        if (trainingTypeId != null) {
            predicates.add(cb.equal(root.get("trainingType").get("id"), trainingTypeId));
            log.info("Added training type ID predicate for {}", trainingTypeId);
        }
    }

    /**
     * Adds a predicate for filtering by training date and duration if the given training date and duration are not null.
     *
     * @param predicates The list of predicates.
     * @param root       The root entity.
     * @param cb         The criteria builder.
     */
    private void addTrainingDateAndDurationPredicate(List<Predicate> predicates, Root<Training> root, CriteriaBuilder cb) {
        if (trainingDate != null && trainingDuration != null) {
            Date endDate = new Date(trainingDate.getTime() + trainingDuration * 60000); // converting duration to milliseconds
            predicates.add(cb.between(root.get("date"), trainingDate, endDate));
            log.info("Added training date and duration predicate for {} and {}", trainingDate, trainingDuration);
        }
    }
}
