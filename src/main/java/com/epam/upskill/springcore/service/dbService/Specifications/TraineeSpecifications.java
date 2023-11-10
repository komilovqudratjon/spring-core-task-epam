package com.epam.upskill.springcore.service.dbService.Specifications;

import com.epam.upskill.springcore.model.Trainee;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @className: TraineeSpecifications  $
 * @description: TODO
 * @date: 09 November 2023 $
 * @time: 4:18 PM 14 $
 * @author: Qudratjon Komilov
 */
public class TraineeSpecifications implements Specification<Trainee> {


    private Date dateOfBirth;

    private String address;

    public TraineeSpecifications(Date dateOfBirth, String address) {
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    @Override
    public Predicate toPredicate(Root<Trainee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (dateOfBirth != null) {
            predicates.add(criteriaBuilder.equal(root.get("dateOfBirth"), dateOfBirth));
        }
        if (address != null) {
            predicates.add(criteriaBuilder.equal(root.get("address"), address));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
