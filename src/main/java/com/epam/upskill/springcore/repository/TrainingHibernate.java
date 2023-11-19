package com.epam.upskill.springcore.repository;

import com.epam.upskill.springcore.model.Training;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @description: Repository interface for Training entity.
 * @date: 08 November 2023 $
 * @time: 5:38 AM 25 $
 * @author: Qudratjon Komilov
 */


@Service
@Slf4j

public class TrainingHibernate extends CrudRepository<Training, Long> {

    public TrainingHibernate() {
        super(Training.class);
    }


}

