package com.epam.upskill.springcore.repository;

import com.epam.upskill.springcore.model.Specialization;
import org.springframework.stereotype.Service;

/**
 * @description: TODO
 * @date: 19 November 2023 $
 * @time: 2:48 AM 09 $
 * @author: Qudratjon Komilov
 */
@Service
public class SpecializationHibernate extends CrudRepository<Specialization, Long> {
    public SpecializationHibernate() {
        super(Specialization.class);
    }

}
