package com.epam.upskill.springcore.service.dbService.Specifications;

import com.epam.upskill.springcore.model.Training;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @className: TrainingSpecifications  $
 * @description: TODO
 * @date: 09 November 2023 $
 * @time: 4:18 PM 14 $
 * @author: Qudratjon Komilov
 */
@Builder
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

    @Override
    public Predicate toPredicate(Root<Training> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        // Filter by trainee ID
        if (traineeId != null) {
            predicates.add(criteriaBuilder.equal(root.get("trainee").get("id"), this.traineeId));
        }

        // Filter by trainer ID
        if (trainerId != null) {
            predicates.add(criteriaBuilder.equal(root.get("trainer").get("id"), this.trainerId));
        }

        // Filter by training name
        if (trainingName != null && !trainingName.isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + trainingName.toLowerCase() + "%"));
        }

        // Filter by training type ID
        if (trainingTypeId != null) {
            predicates.add(criteriaBuilder.equal(root.get("trainingType").get("id"), this.trainingTypeId));
        }

        // Filter by training date and duration
        if (trainingDate != null && trainingDuration != null) {
            // Assuming trainingDuration is in minutes and you want to find trainings within this duration from the start date
            Date endDate = new Date(trainingDate.getTime() + trainingDuration * 60000); // converting duration to milliseconds
            predicates.add(criteriaBuilder.between(root.get("date"), trainingDate, endDate));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
