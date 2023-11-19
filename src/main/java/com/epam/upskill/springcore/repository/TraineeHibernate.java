package com.epam.upskill.springcore.repository;

import com.epam.upskill.springcore.model.Trainee;
import org.springframework.stereotype.Service;

/**
 * @description: Repository interface for Trainee entity.
 * @date: 08 November 2023 $
 * @time: 5:37 AM 43 $
 * @author: Qudratjon Komilov
 */


@Service
public class TraineeHibernate extends CrudRepository<Trainee, Long> {
    public TraineeHibernate() {
        super(Trainee.class);
    }

}

