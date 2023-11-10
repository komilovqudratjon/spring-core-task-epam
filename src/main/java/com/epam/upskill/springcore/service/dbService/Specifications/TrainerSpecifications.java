package com.epam.upskill.springcore.service.dbService.Specifications;

import com.epam.upskill.springcore.model.Trainer;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @className: TrainerSpecifications  $
 * @description: TODO
 * @date: 09 November 2023 $
 * @time: 4:18 PM 14 $
 * @author: Qudratjon Komilov
 */
public class TrainerSpecifications implements Specification<Trainer> {

    @Override
    public Predicate toPredicate(Root<Trainer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
