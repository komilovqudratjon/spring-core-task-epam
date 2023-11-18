package com.epam.upskill.springcore.repository;

import com.epam.upskill.springcore.model.TrainingType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @description: Repository interface for TrainingType entity.
 * @date: 08 November 2023 $
 * @time: 5:36 AM 48 $
 * @author: Qudratjon Komilov
 */


@Service
@Slf4j

public class TrainingTypeHibernate extends CrudRepository<TrainingType, Long> {


    public TrainingTypeHibernate(Class<TrainingType> entityType) {
        super( entityType);
    }


}
