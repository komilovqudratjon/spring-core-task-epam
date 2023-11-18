package com.epam.upskill.springcore.repository;

import com.epam.upskill.springcore.model.Trainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TrainerHibernate extends CrudRepository<Trainer, Long> {

    public TrainerHibernate(Class<Trainer> entityType) {
        super( entityType);
    }


}