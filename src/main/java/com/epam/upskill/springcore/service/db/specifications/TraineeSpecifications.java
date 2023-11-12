package com.epam.upskill.springcore.service.db.specifications;

import com.epam.upskill.springcore.model.Trainee;
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
 * @description: Specification for filtering Trainee entities based on certain criteria.
 * It builds a dynamic query based on the given date of birth and address.
 * @date: 09 November 2023 $
 * @time: 4:18 PM 14 $
 * @author: Qudratjon Komilov
 */
@Slf4j
public class TraineeSpecifications implements Specification<Trainee> {

    private Date dateOfBirth;
    private String address;

    public TraineeSpecifications(Date dateOfBirth, String address) {
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    /**
     * Builds a dynamic query based on the given date of birth and address.
     *
     * @param root            The root entity.
     * @param query           The query.
     * @param criteriaBuilder The criteria builder.
     * @return The built predicate.
     */
    @Override
    public Predicate toPredicate(Root<Trainee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        addDateOfBirthPredicateIfNotNull(predicates, root, criteriaBuilder);
        addAddressPredicateIfNotNull(predicates, root, criteriaBuilder);

        Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        log.trace("Built predicate: {}", finalPredicate);
        return finalPredicate;
    }

    /**
     * Adds a predicate for filtering by date of birth if the given date of birth is not null.
     *
     * @param predicates      The list of predicates.
     * @param root            The root entity.
     * @param criteriaBuilder The criteria builder.
     */
    private void addDateOfBirthPredicateIfNotNull(List<Predicate> predicates, Root<Trainee> root, CriteriaBuilder criteriaBuilder) {
        if (dateOfBirth != null) {
            predicates.add(criteriaBuilder.equal(root.get("dateOfBirth"), dateOfBirth));
            log.info("Added date of birth predicate for {}", dateOfBirth);
        }
    }

    /**
     * Adds a predicate for filtering by address if the given address is not null.
     *
     * @param predicates      The list of predicates.
     * @param root            The root entity.
     * @param criteriaBuilder The criteria builder.
     */
    private void addAddressPredicateIfNotNull(List<Predicate> predicates, Root<Trainee> root, CriteriaBuilder criteriaBuilder) {
        if (address != null) {
            predicates.add(criteriaBuilder.equal(root.get("address"), address));
            log.info("Added address predicate for {}", address);
        }
    }
}
