package com.epam.upskill.springcore.service.db.specifications;

import com.epam.upskill.springcore.model.Training;
import com.epam.upskill.springcore.model.TrainingType;
import com.epam.upskill.springcore.model.Users;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: TODO This class is used for TrainingSpecifications like filter
 * @date: 07 December 2023 $
 * @time: 3:15 AM 05 $
 * @author: Qudratjon Komilov
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TrainingSpecifications implements Specification<Training> {
    private String usernameTrainee;
    private String usernameTrainer;
    private Date periodFrom;
    private Date periodTo;
    private String trainerName;
    private String traineeName;
    private String trainingType;

    @Override
    public Predicate toPredicate(Root<Training> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder ){

        List<Predicate> predicates = new ArrayList<>();

        Join<Training, Users> trainerUserJoin = root.join("trainer").join("user");
        Join<Training, Users> traineeUserJoin = root.join("trainee").join("user");

        if (usernameTrainee != null) {
            predicates.add(criteriaBuilder.equal(traineeUserJoin.get("username"), usernameTrainee));
        }

        if (trainerName != null) {
            Predicate trainerFirstName = criteriaBuilder.like(criteriaBuilder.lower(trainerUserJoin.get("firstName")), "%" + trainerName.toLowerCase() + "%");
            Predicate trainerLastName = criteriaBuilder.like(criteriaBuilder.lower(trainerUserJoin.get("lastName")), "%" + trainerName.toLowerCase() + "%");
            predicates.add(criteriaBuilder.or(trainerFirstName, trainerLastName));
        }

        if (traineeName != null) {
            Predicate traineeFirstName = criteriaBuilder.like(criteriaBuilder.lower(traineeUserJoin.get("firstName")), "%" + traineeName.toLowerCase() + "%");
            Predicate traineeLastName = criteriaBuilder.like(criteriaBuilder.lower(traineeUserJoin.get("lastName")), "%" + traineeName.toLowerCase() + "%");
            predicates.add(criteriaBuilder.or(traineeFirstName, traineeLastName));
        }

        if (usernameTrainer != null) {
            predicates.add(criteriaBuilder.equal(trainerUserJoin.get("username"), usernameTrainer));
        }

        if (periodFrom != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("trainingDate"), periodFrom));
        }
        if (periodTo != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("trainingDate"), periodTo));
        }
        Join<Training, TrainingType> trainingTrainingTypeJoin = root.join("trainingType");

        if (trainingType != null) {
            predicates.add(criteriaBuilder.equal(trainingTrainingTypeJoin.get("trainingTypeName"), trainingType));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
